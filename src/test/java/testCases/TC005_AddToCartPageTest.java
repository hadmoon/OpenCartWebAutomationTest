package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseClass;

public class TC005_AddToCartPageTest extends BaseClass {
	@Test(groups = { "Master" })
	public void verify_addToCart() throws InterruptedException {
		logger.info(" Starting TC_005_AddToCartPageTest ");

		try {

			HomePage hp = new HomePage(getDriver());

			hp.enterProductName("iPhone");
			hp.clickSearch();

			SearchPage sp = new SearchPage(getDriver());

			if (sp.isProductExist("iPhone")) {
				sp.selectProduct("iPhone");
				sp.setQuantity("2");
				sp.addToCart();

			}

			Assert.assertEquals(sp.checkConfMsg(), true);

		} catch (Exception e) {
			logger.error(e.getMessage());
			Assert.fail();
		} finally {
			logger.info(" Finished TC_004_SearchProductTest ");
		}

	}
}
