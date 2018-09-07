package chromeOptions;

import java.io.File;
import java.io.IOException;
import org.apache.commons.mail.EmailException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import lib.BrowserDriverUtility;
import lib.EmailWithAttachmentUtility;
import lib.ExtentReportUtility;
import lib.ScreenshotUtility;

public class ChromeOptionsUse {
	WebDriver dr;
	ExtentReports report = ExtentReportUtility.InvokeExtentReport();
	ExtentTest logger = report.createTest("Handling ChromeOptions");
	String path;
	ChromeOptions options;

	@BeforeTest
	public void InvokeBrowser() {
		try {
			// To add capabilities you can use DesiredCapabilities but here you have to use
			// ChromeOptions merge method to use further.
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setAcceptInsecureCerts(true);
			options.merge(cap);

			// OR you can use ChromeOptions object
			options = new ChromeOptions();
			options.addExtensions(new File("C:\\Chetan\\SeleniumSuite\\AlexaExtensionForChrome\\Alexa.crx"));
			options.addArguments("--disable-infobars");
			// options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			options.setAcceptInsecureCerts(true);

			dr = BrowserDriverUtility.InvokeBrowser("webdriver.chrome.driver",
					"C:\\Chetan\\SeleniumSuite\\WebDrivers\\chromedriver.exe", "http://www.google.com", options);

			path = ScreenshotUtility.CaptureScreenshot(dr, "1_MainPage");
			logger.pass("Main Page - Screenshot taken.", MediaEntityBuilder.createScreenCaptureFromPath(path).build());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void ShowExtension() {
		System.out.println("You can see, Alexa Extension is Appeared...!");
	}

	@AfterTest
	public void tearDown() {
		try {
			EmailWithAttachmentUtility.SendEmail("Test Case Passed - Extension of Alexa added and infobar disabled",
					"Congratulations...!!!", path, "Screenshot of checkbox which are working fine...!!!");
			report.flush();
			dr.close();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
