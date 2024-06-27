package AniketTestAcademy.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import AniketTestAcademy.pageObjects.CartPage;
import AniketTestAcademy.pageObjects.CheckOutPage;
import AniketTestAcademy.pageObjects.ConfirmationPage;
import AniketTestAcademy.pageObjects.LandingPage;
import AniketTestAcademy.pageObjects.ProductCatalogue;
import AniketTestAcademy.testComponents.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImpl extends BaseTest {
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce page")
	public void I_landed_on_Ecommerce_page() throws IOException {
		landingPage = launchApplication();

	}
	
	 @Given("^Login with username (.+) and password (.+)$")
	 public void Login_with_username_and_password (String username, String password) {
		 productCatalogue = landingPage.loginApplication(username, password);
	 }
	 
	 @When("^I add product (.+) to cart$")
	 public void I_add_product_to_cart(String productName) throws InterruptedException {
		 productCatalogue.addProductToCart(productName);
	 }
	 
	 @And("^Checkout the (.+) and submit the order$")
	 public void Checkout_the_and_submit_the_order(String productName) throws Exception {
		    CartPage cartPage =  productCatalogue.goToCartPge();
			Boolean match = cartPage.verifyProductDisplay(productName);
			Assert.assertTrue(match);
			CheckOutPage checkOutPage = cartPage.goToCheckout();
			checkOutPage.selectCountry("india");
			confirmationPage = checkOutPage.submitOrder();
	 }
	 
	 @Then("{string} message displayed on ConfirmationPage")
	 public void message_displayed_on_ConfirmationPage(String string) {
		 String confirmMessage = confirmationPage.getConfirmationMessage();
		 Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		 driver.close();
	 }
	 
	 @Then("{string} message is displayed")
	 public void message_is_displayed(String string) {
		 Assert.assertEquals(string, landingPage.getErrorMessage());
		 driver.close();
	 }
}