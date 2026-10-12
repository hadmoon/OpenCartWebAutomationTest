package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest {
	public class TC_002_LoginTest extends BaseClass {

		@Test(groups = {"Master", "Sanity"})
		public void verify_login() {
			logger.info("**** Starting TC_002_LoginTest  ****");
			logger.debug("capturing application debug logs....");
			try {
				// Home page
				HomePage hp = new HomePage(getDriver());
				hp.clickMyAccount();
				logger.info("clicked on myaccount link on the home page..");
				hp.clickLogin(); // Login link under MyAccount
				logger.info("clicked on login link under myaccount..");

				// Login page
				LoginPage lp = new LoginPage(getDriver());
				logger.info("Entering valid email and password..");
				lp.setEmail(p.getProperty("email"));
				lp.setPassword(p.getProperty("password"));
				lp.clickLogin(); // Login button
				logger.info("clicked on login button..");

				// My Account Page
				MyAccountPage macc = new MyAccountPage(getDriver());

				boolean targetPage = macc.isMyAccountPageExists();
				
				if(targetPage==true) {
					Assert.assertTrue(true);
				}else {
					logger.error("TC002: Login failed");
					Assert.assertTrue(false);
				}
			} catch (Exception e) {
				logger.error("An exception occurred: " + e.getMessage());
				Assert.fail("An exception occurred: " + e.getMessage());
			}

			finally {
				logger.info("***** Finished TC001_AccountRegistrationTest *****");
			}
		}
	}
}
