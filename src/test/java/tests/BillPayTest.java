package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pages.AccountsOverviewPage;
import pages.BillPayPage;
import pages.LoginPage;

public class BillPayTest extends BaseTest {

    private static final String USERNAME = System.getProperty("parabank.username", "");
    private static final String PASSWORD = System.getProperty("parabank.password", "");
    private static final String PAYEE_ACCOUNT = System.getProperty("parabank.payeeAccount", "");

    @Test
    public void testBillPayment() {
        if (USERNAME.isBlank() || PASSWORD.isBlank() || PAYEE_ACCOUNT.isBlank()) {
            throw new SkipException("Set credentials and -Dparabank.payeeAccount to run the bill-pay test.");
        }

        AccountsOverviewPage overview = new LoginPage(driver).loginAs(USERNAME, PASSWORD);
        Assert.assertTrue(overview.isLoaded(), "Login did not reach the Accounts Overview page");

        BillPayPage billPayPage = overview.goToBillPay();
        billPayPage.enterPayeeDetails(
                "Test Utility", "1 Main Street", "Boston", "MA", "02108",
                "555-0100", PAYEE_ACCOUNT, "10");
        billPayPage.selectFromAccountByIndex(0);
        billPayPage.sendPayment();

        Assert.assertEquals(billPayPage.getResultHeadingText(), "Bill Payment Complete",
                "Expected a bill payment confirmation heading");
    }
}
