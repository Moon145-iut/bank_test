package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/** Represents ParaBank's Bill Pay page (billpay.htm). */
public class BillPayPage {

    private final WebDriver driver;

    private final By payeeName = By.name("payee.name");
    private final By payeeStreet = By.name("payee.address.street");
    private final By payeeCity = By.name("payee.address.city");
    private final By payeeState = By.name("payee.address.state");
    private final By payeeZipCode = By.name("payee.address.zipCode");
    private final By payeePhone = By.name("payee.phoneNumber");
    private final By payeeAccount = By.name("payee.accountNumber");
    private final By verifyAccount = By.name("verifyAccount");
    private final By amount = By.name("amount");
    private final By fromAccount = By.name("fromAccountId");
    private final By sendPaymentButton = By.cssSelector("input[value='Send Payment']");
    private final By resultHeading = By.cssSelector("h1.title");
    private final By amountResult = By.id("amount");

    public BillPayPage(WebDriver driver) {
        this.driver = driver;
    }

    public BillPayPage enterPayeeDetails(String name, String street, String city,
                                          String state, String zipCode, String phone,
                                          String accountNumber, String amountValue) {
        type(payeeName, name);
        type(payeeStreet, street);
        type(payeeCity, city);
        type(payeeState, state);
        type(payeeZipCode, zipCode);
        type(payeePhone, phone);
        type(payeeAccount, accountNumber);
        type(verifyAccount, accountNumber);
        type(amount, amountValue);
        return this;
    }

    public void selectFromAccountByIndex(int index) {
        new Select(driver.findElement(fromAccount)).selectByIndex(index);
    }

    public void sendPayment() {
        driver.findElement(sendPaymentButton).click();
    }

    public String getResultHeadingText() {
        return driver.findElement(resultHeading).getText();
    }

    public String getAmountResultText() {
        return driver.findElement(amountResult).getText();
    }

    private void type(By locator, String value) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }
}
