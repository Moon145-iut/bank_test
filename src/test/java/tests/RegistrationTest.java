package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pages.RegistrationPage;

public class RegistrationTest extends BaseTest {

    @Test
    public void testRegistrationAndDuplicateUsername() {
        if (!Boolean.parseBoolean(System.getProperty("registration.enabled", "false"))) {
            throw new SkipException("Set -Dregistration.enabled=true to run the live registration test.");
        }

        String username = "qa_" + System.currentTimeMillis();
        String password = "ParaBank123!";
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.enterCustomerDetails(
                "Test", "User", "1 Main Street", "Boston", "MA", "02108",
                "555-0100", "123-45-6789", username, password);
        registrationPage.submit();

        Assert.assertTrue(driver.getCurrentUrl().contains("overview.htm"),
                "New registration should log the customer in automatically");

        driver.get(BASE_URL.replace("index.htm", "register.htm"));
        registrationPage.enterCustomerDetails(
                "Duplicate", "User", "1 Main Street", "Boston", "MA", "02108",
                "555-0101", "123-45-6790", username, password);
        registrationPage.submit();

        Assert.assertTrue(registrationPage.isErrorDisplayed(),
                "Reusing a username should display a registration error");
    }
}
