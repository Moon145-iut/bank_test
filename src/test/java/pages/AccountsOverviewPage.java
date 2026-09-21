package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Represents the "Accounts Overview" page (overview.htm) shown right after login. */
public class AccountsOverviewPage {

    private final WebDriver driver;

    private final By pageTitle = By.cssSelector("h1.title");
    private final By transferFundsLink = By.linkText("Transfer Funds");
    private final By billPayLink = By.linkText("Bill Pay");
    private final By logoutLink = By.linkText("Log Out");

    public AccountsOverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("overview.htm"));
        return driver.getCurrentUrl().contains("overview.htm");
    }

    public TransferFundsPage goToTransferFunds() {
        driver.findElement(transferFundsLink).click();
        return new TransferFundsPage(driver);
    }

    public BillPayPage goToBillPay() {
        driver.findElement(billPayLink).click();
        return new BillPayPage(driver);
    }

    public void logout() {
        driver.findElement(logoutLink).click();
    }
}
