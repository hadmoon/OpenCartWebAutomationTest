package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseClass;

public class TC004_SearchProductTest extends BaseClass {
	@Test(groups = { "Master" })
	public void verify_pruductSearch() throws InterruptedException {
		logger.info(" Starting TC_004_SearchProductTest ");

		try {

			HomePage hm = new HomePage(getDriver());

			// hm.enterProductName("iPhone");
			hm.enterProductName("mac");

			hm.clickSearch();

			SearchPage sp = new SearchPage(getDriver());
			sp.isProductExist("MacBook");

			Assert.assertEquals(sp.isProductExist("MacBook"), true);

		} catch (Exception e) {
			logger.error(e.getMessage());
			Assert.fail();
		} finally {
			logger.info(" Finished TC_004_SearchProductTest ");

		}

	}
}
