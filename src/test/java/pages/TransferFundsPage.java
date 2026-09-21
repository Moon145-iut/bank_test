package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/** Represents the "Transfer Funds" page (transfer.htm). */
public class TransferFundsPage {

    private final WebDriver driver;

    private final By amountField = By.id("amount");
    private final By fromAccountDropdown = By.id("fromAccountId");
    private final By toAccountDropdown = By.id("toAccountId");
    private final By transferButton = By.cssSelector("input[value='Transfer']");
    private final By resultHeading = By.cssSelector("h1.title");
    private final By transferredAmountResult = By.id("amountResult");

    public TransferFundsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterAmount(String amount) {
        driver.findElement(amountField).clear();
        driver.findElement(amountField).sendKeys(amount);
    }

    /** Selects a "from" account by its visible index in the dropdown (0 = first account). */
    public void selectFromAccountByIndex(int index) {
        new Select(driver.findElement(fromAccountDropdown)).selectByIndex(index);
    }

    public void selectToAccountByIndex(int index) {
        new Select(driver.findElement(toAccountDropdown)).selectByIndex(index);
    }

    public void clickTransfer() {
        driver.findElement(transferButton).click();
    }

    public String getResultHeadingText() {
        return driver.findElement(resultHeading).getText();
    }

    public String getTransferredAmountText() {
        return driver.findElement(transferredAmountResult).getText();
    }
}
