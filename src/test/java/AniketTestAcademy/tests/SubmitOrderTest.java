package AniketTestAcademy.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import AniketTestAcademy.pageObjects.CartPage;
import AniketTestAcademy.pageObjects.CheckOutPage;
import AniketTestAcademy.pageObjects.ConfirmationPage;
import AniketTestAcademy.pageObjects.OrderPage;
import AniketTestAcademy.pageObjects.ProductCatalogue;
import AniketTestAcademy.testComponents.BaseTest;

public class SubmitOrderTest extends BaseTest {
	public static final Logger logger = BaseTest.logger;
	String productName = "ZARA COAT 3";
	
	@Test (priority = 1)
	public void loginAndOut() throws Exception {
		logger.info("Starting loginAndOut test");
		landingPage.loginApplication();
		logger.info("Logged in successfully");
		landingPage.signOutApplication();
		logger.info("Signed out successfully");
		Thread.sleep(2000);
	}
	@Test (priority = 2, dataProvider= "getData", groups={"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws Exception {
		logger.info("Starting submitOrder test with data: {}", input);
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		logger.info("Logged in successfully with user: {}", input.get("email"));
		productCatalogue.addProductToCart(input.get("product"));
		logger.info("Added product to cart: {}", input.get("product"));
		CartPage cartPage =  productCatalogue.goToCartPge();
		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		logger.info("Verified product display in cart: {}", input.get("product"));
		CheckOutPage checkOutPage = cartPage.goToCheckout();
		checkOutPage.selectCountry("india");
		logger.info("Selected country: india");
		ConfirmationPage confirmationPage = checkOutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		Thread.sleep(2000);
		logger.info("Order submitted successfully for product: {}", input.get("product"));
	}
	
	@Test (dependsOnMethods = "submitOrder" , priority = 3)
	public void orderHistorytest() {
		logger.info("Starting orderHistorytest");
		ProductCatalogue productCatalogue = landingPage.loginApplication();
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
		logger.info("Verified order display in order history for product: {}", productName);
	}
	//1st Method
//	@DataProvider
//	public Object[][] getData() {
//		return new Object[][] {{"aniketgapat@test.com","Aniket@1234","ZARA COAT 3"}, {"shetty@gmail.com","Shetty@1234","ADIDAS ORIGINAL" } };
//	} //public void submitOrder(String email, String password, String productName) - required declaration of method
	
	//2nd Method
//	@DataProvider
//	public Object[][] getData() {
//		HashMap<String,String> map = new HashMap<String, String>();
//		map.put("email", "aniketgapat@test.com");
//		map.put("password", "Aniket@1234");
//		map.put("product", "ZARA COAT 3");
//		
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map.put("email", "shetty@gmail.com");
//		map.put("password", "Shetty@1234");
//		map.put("product", "ADIDAS ORIGINAL");
//		
//		return new Object[][] {{map}, {map1}};
//	} //public void submitOrder(HashMap<String,String> input) - required declaration of method
	
	//3rd Method
	@DataProvider
	public Object[][] getData() throws IOException {
		logger.info("Fetching test data from JSON file for submitOrder test");
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	} //public void submitOrder(HashMap<String,String> input) - required declaration of method (method defined in BaseTest)

}
