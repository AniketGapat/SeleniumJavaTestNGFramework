package AniketTestAcademy.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
	
	String productName = "ZARA COAT 3";
	
	@Test (priority = 1)
	public void loginAndOut() throws Exception {
		landingPage.loginApplication("aniketgapat@test.com", "Aniket@1234");
		landingPage.signOutApplication();
		Thread.sleep(2000);
	}
	@Test (priority = 2, dataProvider= "getData", groups={"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws Exception {
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage =  productCatalogue.goToCartPge();
		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckOutPage checkOutPage = cartPage.goToCheckout();
		checkOutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkOutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		Thread.sleep(2000);
		
	}
	
	@Test (dependsOnMethods = "submitOrder" , priority = 3)
	public void orderHistorytest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("aniketgapat@test.com", "Aniket@1234");
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
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
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\AniketTestAcademy\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	} //public void submitOrder(HashMap<String,String> input) - required declaration of method (method defined in BaseTest)

}
