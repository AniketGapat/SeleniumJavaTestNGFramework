package AniketTestAcademy.pageObjects;

import AniketTestAcademy.resources.ConfigReader;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import AniketTestAcademy.AbstractComponents.AbstractComponents;

import java.io.IOException;

public class LandingPage extends AbstractComponents {
	
	WebDriver driver;
	ConfigReader configReader;
	public LandingPage (WebDriver driver) throws IOException {
		super(driver);
		this.driver= driver;
		configReader = new ConfigReader();
		PageFactory.initElements(driver, this);
	}
	
	// Locate elements using pagefactory
	@FindBy(id="userEmail") 
	private WebElement userEmail;
	
	@FindBy(id="userPassword") 
	private WebElement userPassword;
	
	@FindBy(id="login") 
	private WebElement submit;
	
	@FindBy(css="[class*='flyInOut']")
	private WebElement errorMessage;

	// Overloaded method with parameters
	public ProductCatalogue loginApplication(String username, String password) {
		userEmail.sendKeys(username);
		userPassword.sendKeys(password);
		submit.click();
		return new ProductCatalogue(driver);
	}

	// No-argument method uses ConfigReader
	public ProductCatalogue loginApplication() {
		return loginApplication(configReader.getUserName(), configReader.getPassword());
	}
	
	public String getErrorMessage()
	{
		waitForWebElementToAppear(errorMessage);
		logger.info("Error message displayed: {}", errorMessage.getText());
		return errorMessage.getText();
	}
	
	public void goTo() {
		driver.get(configReader.getApplicationUrl());
	}
	
}
