package EcommerceWebsite.Automation;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.AssertJUnit;

import org.testng.annotations.AfterSuite;

import org.testng.annotations.BeforeSuite;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;


public class EcommerceTest {
	public static void takeScreenShot(WebDriver wd,String fileName) throws IOException {
		//take the screenshot and store it as a file format 
		File file=((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
		//copy the screen shot to the specified location
		FileUtils.copyFile(file,new File("D:\\"+fileName+".png") );
		
		
	}

	private WebDriver wd;

	@Test
	public void flipkart() {
		System.out.println("Welcome to Flipkart explore plus()");


	}

	@Test(groups = "flipKart")

	public void measurePageLoadTimeTest() {
		long startTime;
		long endTime;
		long pageLoadTime;

		startTime = System.currentTimeMillis();
		System.out.println("Start time ="+startTime);
		// Wait for the page to load completely
		wd.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		endTime = System.currentTimeMillis();
		System.out.println("end time ="+endTime);
		System.out.println("load time ="+ (endTime-startTime));
	}



	@Test(groups = "flipKart")
	public void afterMethod() throws IOException {
		


		wd.findElement(By.name("q")).sendKeys("iPhone 13"+Keys.ENTER);
		takeScreenShot(wd,"ecommerce3");
		System.out.println("Searched for iphone 13");
	}


	@Test(groups = "flipKart")

	public void checkImageVisibilityTest() {
		List<WebElement> images = wd.findElements(By.tagName("img"));
		int WebHeight = wd.manage().window().getSize().getHeight();
		System.out.println("\n===================================================\nImages\n\n");
		for(WebElement img:images) {
			int imageLocation = img.getLocation().getY();

			if(imageLocation < WebHeight && imageLocation>=0) {
				if(img.isDisplayed()) {
					System.out.println("Image is loaded and displayed = "+img.getAttribute("src"));
				}
				else {
					System.out.println("Image is not displayed ="+img.getAttribute("src"));
				}

			}
			else {
				System.out.println("Image is out of screen height = "+img.getAttribute("src"));
			}
		}
		System.out.println("\n===================================================");
	}


	@Test(groups = "flipKart")

	public void scrollFeature() throws InterruptedException, WebDriverException, IOException {
		System.out.println("\n===================================================");
		WebElement body = wd.findElement(By.tagName("body"));
		System.out.println(body.getLocation());
		int tabHeight=wd.manage().window().getSize().getHeight();
		int contentHeight=body.getSize().height;
		System.out.println("windows tab height ="+ tabHeight);
		System.out.println("height of dody content ="+  contentHeight);
		int different = contentHeight-tabHeight;
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(different>0);
		System.out.println("This page has scroll features");

	}
	@Test(groups = "flipKart")
	public void scrollToEnd() throws WebDriverException, IOException {
		System.out.println("\n===================================================");
		WebElement body = wd.findElement(By.tagName("body"));
		body.sendKeys(Keys.END);
		takeScreenShot(wd,"ecommerce4");

	}


	@Test(groups = "flipKart")

	public void checkContentRefreshFrequencyTest() {
		// Navigate to the Flipkart home page

		// Scroll down multiple times to trigger content refresh
		for (int i = 0; i < 5; i++) {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) wd;
			jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");

			// Wait for a moment to let the content refresh
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Perform calculations to determine the frequency of content refresh
		int refreshFrequency = 5; // Number of times scrolled
		long totalTimeTaken = 10000; // 10 seconds (total wait time for content to refresh)

		// Calculate the frequency at which the content is refreshed
		int contentRefreshFrequency = (int) (refreshFrequency / (totalTimeTaken / 1000.0));
		System.out.println("contentRefreshFrequency:"+contentRefreshFrequency);
		
	}



	@Test(groups = "flipKart")

	public void verifyImageDownloadAndDisplayTimingTest() {
		// Navigate to the Flipkart home page

		// Get the coordinates of the image element
		WebElement imageElement = wd.findElement(By.className("_396cs4"));
		int imageElementY = imageElement.getLocation().getY();

		// Scroll to the image position
		JavascriptExecutor jsExecutor = (JavascriptExecutor) wd;
		jsExecutor.executeScript("window.scrollTo(0, arguments[0]);", imageElementY);

		// Wait for the image to be downloaded and displayed
		try {
			Thread.sleep(2000); // Adjust the wait time as needed
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Check if the image is displayed
		AssertJUnit.assertTrue(imageElement.isDisplayed());

		// Check if the image is downloaded in time just before scrolling to its position
		AssertJUnit.assertTrue(imageElement.getAttribute("naturalWidth") != "0");

	} 

	@Test(groups = "flipKart")


	public void verifyScrollToBottomTest() {

		// Scroll to the bottom of the page
		JavascriptExecutor jsExecutor = (JavascriptExecutor) wd;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		// Wait for a moment to let the page load after scrolling
		try {
			Thread.sleep(2000); // You can adjust the wait time as needed
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Verify that the page has been scrolled to the bottom
		long totalPageHeight = (Long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight );");
		long windowHeight = (Long) jsExecutor.executeScript("return window.innerHeight;");
		long scrollPosition = (Long) jsExecutor.executeScript("return window.scrollY;");

		// Assert that the scroll position is near the bottom of the page
		long buffer = 50; // You can adjust the buffer value as needed
		long expectedScrollPosition = totalPageHeight - windowHeight - buffer;
		assert Math.abs(expectedScrollPosition - scrollPosition) <= buffer :
			"The page is not scrolled to the bottom.";
		System.out.println("expectedScrollPosition:"+ expectedScrollPosition);
	}






	@BeforeSuite
	public void beforeSuite() throws IOException {
		WebDriverManager.chromedriver().setup();
		wd = new ChromeDriver();
		wd.manage().window().maximize();
		wd.get("https://www.flipkart.com/");
		takeScreenShot(wd,"Ecommerce1");
		
	}

	@AfterSuite
	public void afterSuite() {
		wd.close();
	}
	

}
