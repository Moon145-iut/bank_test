package listeners;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Registered in testng.xml. Creates one HTML report per run at
 * reports/ExtentReport.html, with a screenshot attached automatically
 * whenever a test fails.
 */
public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;
    private static final Map<Long, ExtentTest> testMap = new ConcurrentHashMap<>();
    private static final String REPORTS_DIR = System.getProperty("user.dir") + "/reports";

    private static synchronized ExtentReports getReportInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(REPORTS_DIR + "/ExtentReport.html");
            spark.config().setDocumentTitle("ParaBank Automation Report");
            spark.config().setReportName("Regression Suite");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }

    @Override
    public void onStart(ITestContext context) {
        getReportInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = getReportInstance().createTest(
                result.getMethod().getMethodName(),
                result.getMethod().getDescription());
        testMap.put(Thread.currentThread().getId(), test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testMap.get(Thread.currentThread().getId()).pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testMap.get(Thread.currentThread().getId());
        test.fail(result.getThrowable());

        Object instance = result.getInstance();
        if (instance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) instance).getDriver();
            if (driver != null) {
                String screenshotPath = captureScreenshot(driver, result.getMethod().getMethodName());
                if (screenshotPath != null) {
                    try {
                        // Path relative to the report file so the image link resolves correctly
                        test.addScreenCaptureFromPath("screenshots/" + new File(screenshotPath).getName());
                    } catch (Exception e) {
                        System.out.println("Could not attach screenshot to report: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testMap.get(Thread.currentThread().getId()).skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        getReportInstance().flush();
    }

    private String captureScreenshot(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String dir = REPORTS_DIR + "/screenshots/";
            Files.createDirectories(Paths.get(dir));
            String path = dir + testName + "_" + System.currentTimeMillis() + ".png";
            Files.copy(src.toPath(), Paths.get(path));
            return path;
        } catch (Exception e) {
            System.out.println("Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }
}
