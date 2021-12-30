package testNGConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

	WebDriver driver;
	String browser = "null";

	@BeforeClass
//calling from Configure
	public void readConfig() {
		// 4 class that can read any file= FileReader,InputStream,Buffered,Scanner
		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");	
			prop.load(input);
			prop.getProperty(browser);
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	@BeforeMethod
	public void init() {

		// managing two driver
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://techfios.com/billing/?ng=admin/");

	}

	@Test
	public void testLoginPage() {

		// storing element with By class
		By userNameField = By.xpath("//input[@id='username']");
		By passWordField = By.xpath("//*[@id=\"password\"]");
		By signInButtonField = By.xpath("/html/body/div/div/div/form/div[3]/button");
		By dashboardHeaderField = By.xpath("//h2[contains(text(), 'Dashboard')]");

		// Login Data
		String USER_NAME = "demo@techfios.com";
		String PASSWORD = "abc123";

		// For the login Calling from above
		driver.findElement(userNameField).sendKeys("USER_NAME");
		driver.findElement(passWordField).sendKeys("PASSWORD");
		driver.findElement(signInButtonField).click();

		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), "Wrong Page!!");

	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();

	}
}