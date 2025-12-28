package AniketTestAcademy.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import AniketTestAcademy.resources.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import AniketTestAcademy.pageObjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	public static final Logger logger = LogManager.getLogger(BaseTest.class);
	public ConfigReader configReader;

	public WebDriver initializeDriver() throws IOException {
		
//		Properties prop = new Properties();
//		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/AniketTestAcademy/resources/GlobalData.properties");
//		prop.load(fis);
//		logger.info("Loaded Global properties file");
		configReader = new ConfigReader();
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : configReader.getBrowser();
		// Ternary operator --> if condition is true then first argument execute if false second execute
        logger.info("Browser name is: {}", browserName);

		if(browserName.contains("chrome")) {
			ChromeOptions options =new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless")) {
				options.addArguments("headless");
				logger.info("Running in headless mode");
			}
			driver = new ChromeDriver(options);
			//driver.manage().window().setSize(new Dimension(1440,900));
			driver.manage().window().maximize();
			logger.info("Launched Chrome browser");
		}
		else if(browserName.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			logger.info("Launched Firefox browser");
		}
		else if(browserName.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			logger.info("Launched Edge browser");
		}
		else{
			logger.error("Invalid browser name provided: {}", browserName);
			throw new IllegalArgumentException("Invalid browser name: " + browserName);
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		logger.info("Browser window maximized and implicit wait set to 10 seconds");
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		logger.info("Reading JSON data from file: {}", filePath);
		//read json to string
		String jsonContent = 	FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
	
		//String to HashMap- Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
		logger.info("JSON data successfully converted to List of HashMaps");
		return data;
	
		//{map, map}
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		logger.info("Capturing screenshot for test case: {}", testCaseName);
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") +"//reports//" + testCaseName +"_"+ timestamp +".png");
		FileUtils.copyFile(source, file);
		logger.info("Screenshot saved at: {}", file.getAbsolutePath());
		return System.getProperty("user.dir") + "//reports//" + testCaseName +"_"+ timestamp + ".png";
	}
	
	@BeforeMethod (alwaysRun =true)
	public LandingPage launchApplication() throws IOException {
		logger.info("Launching application");
		 driver = initializeDriver();
		 landingPage = new LandingPage(driver);
		 landingPage.goTo();
		 logger.info("Navigated to landing page");
		 return landingPage;
	}
	
	@AfterMethod (alwaysRun =true)
	public void tearDown() {
		if (driver != null) {
			logger.info("Closing browser and quitting driver");
			driver.quit();
		} else {
			logger.warn("Driver was null at teardown");
		}
	}
}