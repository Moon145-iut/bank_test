package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Represents ParaBank's customer registration page (register.htm). */
public class RegistrationPage {

    private final WebDriver driver;

    private final By firstName = By.id("customer.firstName");
    private final By lastName = By.id("customer.lastName");
    private final By street = By.id("customer.address.street");
    private final By city = By.id("customer.address.city");
    private final By state = By.id("customer.address.state");
    private final By zipCode = By.id("customer.address.zipCode");
    private final By phoneNumber = By.id("customer.phoneNumber");
    private final By ssn = By.id("customer.ssn");
    private final By username = By.id("customer.username");
    private final By password = By.id("customer.password");
    private final By repeatedPassword = By.id("repeatedPassword");
    private final By registerButton = By.cssSelector("input[value='Register']");
    private final By pageTitle = By.cssSelector("h1.title");
    private final By errorMessage = By.cssSelector(".error, #rightPanel p.error");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public RegistrationPage enterCustomerDetails(String firstNameValue, String lastNameValue,
                                                   String streetValue, String cityValue,
                                                   String stateValue, String zipCodeValue,
                                                   String phoneNumberValue, String ssnValue,
                                                   String usernameValue, String passwordValue) {
        type(firstName, firstNameValue);
        type(lastName, lastNameValue);
        type(street, streetValue);
        type(city, cityValue);
        type(state, stateValue);
        type(zipCode, zipCodeValue);
        type(phoneNumber, phoneNumberValue);
        type(ssn, ssnValue);
        type(username, usernameValue);
        type(password, passwordValue);
        type(repeatedPassword, passwordValue);
        return this;
    }

    public void submit() {
        driver.findElement(registerButton).click();
    }

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(errorMessage).isEmpty();
    }

    public String getErrorText() {
        return driver.findElement(errorMessage).getText();
    }

    private void type(By locator, String value) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }
}
