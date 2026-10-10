package Baseclass;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import GenericUtility.PropertyUtility;
import ObjRepo.LoginPage;

public class BaseClass {

	protected WebDriver driver;
	protected LoginPage loginPage;

	@BeforeMethod(alwaysRun = true)
   @Parameters("browser")
	public void setup(String browser) throws Exception {



		// Read browser from common.properties

		//String browser = PropertyUtility.getProperty("browser");

		// Launch browser
		if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions options = new ChromeOptions();

			options.addArguments("--incognito");

			driver = new ChromeDriver(options);

		} else if (browser.equalsIgnoreCase("firefox")) {

			driver = new FirefoxDriver();

		} else if (browser.equalsIgnoreCase("edge")) {

			driver = new EdgeDriver();

		} else {

			throw new RuntimeException("Invalid browser name: " + browser);
		}

		// Browser settings
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

		// Read URL from common.properties
		
		String url = PropertyUtility.getProperty("url");

		// Open application
		driver.get(url);

		// Initialize Login Page
		loginPage = new LoginPage(driver);
	}

	// Reusable login method
	protected void login(String username, String password) {

		loginPage.getUN(username);
		loginPage.getPW(password);
		loginPage.getLogin();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}
	}
}