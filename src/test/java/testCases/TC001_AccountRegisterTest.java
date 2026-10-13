package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegisterPage;
import testBase.BaseClass;

public class TC001_AccountRegisterTest extends BaseClass {

	@Test(groups = {"Master", "Regression"})
	public void verify_account_registration() {
		logger.info("***** Starting TC001_AccountRegistrationTest  ****");
		logger.debug("This is a debug log message");
		try {
			HomePage hp = new HomePage(getDriver());
			hp.clickMyAccount();
			logger.info("Clicked on MyAccount Link.. ");
			hp.clickRegister();
			logger.info("Clicked on Register Link.. ");

			RegisterPage rp = new RegisterPage(getDriver());
			logger.info("Providing customer details...");
			rp.setFirstName(randomString(4));
			rp.setLastName(randomString(6));
			rp.setEmail(randomAlphanumeric(6).concat("@gmail.com"));
			rp.setTelephone(randomNumber(10));
			
			String password=randomString(5).concat("@").concat(randomNumber(5));
			rp.setPassword(password);
			rp.setConfirmPassword(password);
			
			rp.setPrivacyPolicy();
			rp.clickContinue();
			logger.info("Validating expected message..");
			String confmsg = rp.getConfirmationMsg();
			if(confmsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			}else {
				logger.error("TC001: failed: Confirmation message mismatch");
				Assert.assertTrue(false);//Assert not throw exception
			}
		} catch (Exception e) {
			Assert.fail("An exception occurred: " + e.getMessage());
			logger.error("An exception occurred: " + e.getMessage());
		} finally {
			logger.info("***** Finished TC001_AccountRegistrationTest *****");
		}
	}

}
