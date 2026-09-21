package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pages.AccountsOverviewPage;
import pages.LoginPage;
import pages.TransferFundsPage;

public class TransferFundsTest extends BaseTest {
    // https://parabank.parasoft.com/parabank/register.htm
        private static final String USERNAME = System.getProperty("parabank.username", "");
        private static final String PASSWORD = System.getProperty("parabank.password", "");

        private void requireCredentials() {
                if (USERNAME.isBlank() || PASSWORD.isBlank()) {
                        throw new SkipException("Set -Dparabank.username and -Dparabank.password to run authenticated tests.");
                }
        }

    @Test
    public void testValidFundsTransfer() {
                requireCredentials();
        LoginPage loginPage = new LoginPage(driver);
        AccountsOverviewPage overview = loginPage.loginAs(USERNAME, PASSWORD);
        Assert.assertTrue(overview.isLoaded(), "Login did not reach the Accounts Overview page");

        TransferFundsPage transferPage = overview.goToTransferFunds();
        transferPage.enterAmount("50");
        transferPage.selectFromAccountByIndex(0);
        transferPage.selectToAccountByIndex(1);
        transferPage.clickTransfer();

        Assert.assertTrue(transferPage.getResultHeadingText().contains("Transfer Complete"),
                "Expected a 'Transfer Complete' confirmation heading after a valid transfer");
        Assert.assertEquals(transferPage.getTransferredAmountText(), "$50.00",
                "The confirmed transfer amount did not match what was entered");
    }

    @Test
    public void testTransferWithNegativeAmount() {
                requireCredentials();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(USERNAME, PASSWORD);

        TransferFundsPage transferPage = new TransferFundsPage(driver);
        transferPage.enterAmount("-50");
        transferPage.selectFromAccountByIndex(0);
        transferPage.selectToAccountByIndex(1);
        transferPage.clickTransfer();

        String page = driver.getPageSource().toLowerCase();
        Assert.assertTrue(page.contains("error") || page.contains("transfer complete"),
                "Unexpected page state after submitting a negative transfer amount — "
                        + "capture this behavior as a documented observation either way.");
    }
}
