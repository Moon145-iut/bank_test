package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.SkipException;
import pages.LoginPage;
import utils.CsvDataReader;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return CsvDataReader.readCsv("src/test/resources/testdata/login_data.csv");
    }

    @Test(dataProvider = "loginData")
    public void testLoginScenarios(String username, String password, String expectedResult) {
        if (username.startsWith("REPLACE_WITH_YOUR_REGISTERED_USERNAME")) {
            username = System.getProperty("parabank.username", "");
        }
        if (password.startsWith("REPLACE_WITH_YOUR_PASSWORD")) {
            password = System.getProperty("parabank.password", "");
        }
        if (expectedResult.equalsIgnoreCase("success") && (username.isBlank() || password.isBlank())) {
            throw new SkipException("Set -Dparabank.username and -Dparabank.password to run the valid login row.");
        }

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (expectedResult.equalsIgnoreCase("success")) {
            Assert.assertTrue(driver.getCurrentUrl().contains("overview.htm"),
                    "Expected a valid login to land on the Accounts Overview page, but URL was: "
                            + driver.getCurrentUrl());
        } else {
            Assert.assertFalse(driver.getCurrentUrl().contains("overview.htm"),
                    "Expected an invalid login to be rejected, but it reached the Accounts Overview page.");
        }
    }
}
