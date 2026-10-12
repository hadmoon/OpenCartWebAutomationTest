package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	// [SỬA 1] Mỗi thread có driver riêng, thay cho "static public WebDriver driver"
	private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Logger logger;
	public Properties p;

	@BeforeClass(groups = { "Master", "Regression", "Sanity" })
	@Parameters({ "os", "browser" })
	public void setup(String os, String browser) throws IOException {
		// loading properties file
		FileReader file = new FileReader("./src/test/resources/config.properties");
		p = new Properties();
		p.load(file);

		// logger
		logger = LogManager.getLogger(this.getClass());

		// Initialize Driver
		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// os
			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			}else if (os.equalsIgnoreCase("linux")) {
					capabilities.setPlatform(Platform.LINUX);
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else {
				throw new IllegalArgumentException("No matching os: " + os);
			}

			// browser
			switch (browser.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			default:
				throw new IllegalArgumentException("No matching browser: " + browser);
			}
			tlDriver.set(new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(), capabilities));
		}

		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

			switch (browser.toLowerCase()) {
			case "chrome":
				tlDriver.set(new ChromeDriver());
				break;
			case "edge":
				tlDriver.set(new EdgeDriver());
				break;
			case "firefox":
				tlDriver.set(new FirefoxDriver());
				break;
			default:
				System.out.println("No matching browser..");
				return;
			}
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		getDriver().get(p.getProperty("appURL1"));
		getDriver().manage().window().maximize();
	}

	@AfterClass(groups = { "Master", "Regression", "Sanity" })
	public void teardown() {
		// Chỉ quit driver của thread hiện tại, rồi xoá khỏi ThreadLocal
		if (getDriver() != null) {
			getDriver().quit();
			tlDriver.remove();
		}
	}

	public String randomString(int count) {
		return RandomStringUtils.insecure().nextAlphabetic(count);
	}

	public String randomNumber(int count) {
		return RandomStringUtils.insecure().nextNumeric(count);
	}

	public String randomAlphanumeric(int count) {
		return RandomStringUtils.insecure().nextAlphanumeric(count);
	}

	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);// capture screenshot

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);

		sourceFile.renameTo(targetFile);

		return targetFilePath;

	}
}
