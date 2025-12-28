package AniketTestAcademy.tests;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import AniketTestAcademy.pageObjects.CartPage;
import AniketTestAcademy.pageObjects.ProductCatalogue;
import AniketTestAcademy.testComponents.BaseTest;
import AniketTestAcademy.testComponents.Retry;

public class ErrorValidationTest extends BaseTest {
	public static final Logger logger = BaseTest.logger;

	@Test (groups = {"ErrorHandling"}, retryAnalyzer=Retry.class)
	public void loginErrorValidation() {
		Reporter.log("Starting loginErrorValidation test");
		logger.info("Starting loginErrorValidation test");
		landingPage.loginApplication("aniket@test.com", "WrongPassword");
		Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
		logger.info("Completed loginErrorValidation test");
	}
	
	@Test
	public void productErrorValidation() throws InterruptedException {
		Reporter.log("Starting productErrorValidation test");
		logger.info("Starting productErrorValidation test");
		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication();
		logger.info("Logged in successfully and navigating to product catalogue");
		productCatalogue.addProductToCart(productName);
		logger.info("Added product to cart: {}" , productName);
		CartPage cartPage =  productCatalogue.goToCartPge();
		logger.info("Navigated to cart page");
		Boolean match = cartPage.verifyProductDisplay("Zara Coat 3");
		logger.info("Verified product display in cart: {}" , match);
		Assert.assertTrue(match);
		logger.info("Completed productErrorValidation test");
	}

}
