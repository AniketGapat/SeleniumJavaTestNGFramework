package AniketTestAcademy.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import AniketTestAcademy.pageObjects.CartPage;
import AniketTestAcademy.pageObjects.ProductCatalogue;
import AniketTestAcademy.testComponents.BaseTest;
import AniketTestAcademy.testComponents.Retry;

public class ErrorValidations extends BaseTest {
	
	@Test (groups = {"ErrorHandling"}, retryAnalyzer=Retry.class)
	public void loginErrorValidation() {
		
		landingPage.loginApplication("aniketgapat@test.com", "Aniket@123");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void productErrorValidation() throws InterruptedException {
		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("aniketgapat@test.com", "Aniket@1234");
		productCatalogue.addProductToCart(productName);
		CartPage cartPage =  productCatalogue.goToCartPge();
		Boolean match = cartPage.verifyProductDisplay("Zara Coat 3");
		Assert.assertTrue(match);
	}

}
