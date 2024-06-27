package AniketTestAcademy.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AniketTestAcademy.AbstractComponents.AbstractComponents;

public class CartPage extends AbstractComponents {
	
	WebDriver driver;
	
	public CartPage (WebDriver driver) {
		super(driver);
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (xpath="//div[@class='cartSection']/h3")
	List<WebElement> productsTitles;
	
	@FindBy(css = ".totalRow button")
	WebElement checkoutBtn;
	
	public Boolean verifyProductDisplay(String productName) {
		Boolean match = productsTitles.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public CheckOutPage goToCheckout() {
		
		Actions act =new Actions(driver);
		act.moveToElement(checkoutBtn).build().perform();
		checkoutBtn.click();
		return new CheckOutPage(driver);
	}
}
