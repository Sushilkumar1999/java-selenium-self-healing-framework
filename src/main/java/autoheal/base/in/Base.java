package autoheal.base.in;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import autoheal.PageObject.in.LogInPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public LogInPage login;

	public WebDriver getDriver() {
		return driver.get();
	}

	public void initDriver(String browserName) {
		WebDriver localDriver = null;
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			localDriver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			localDriver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			localDriver = new EdgeDriver();
		} else {
			throw new RuntimeException("Invalid browser name: " + browserName);
		}
		localDriver.manage().window().maximize();
//		localDriver .manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.set(localDriver);
	}

	@Parameters("browserName")
	@BeforeMethod(alwaysRun = true)
	public LogInPage launchApplication(String browserName) throws IOException {
		initDriver(browserName);
		login = new LogInPage(getDriver());
		login.goTo(getDataFromProperties("URL"));
		return login;
	}

	@AfterMethod
	public void tearDown() {
		getDriver().quit();
		driver.remove();
	}

	public List<HashMap<String, String>> getDataFromJsonFile(String filePath) throws IOException {
		// Read JSON to String
		String dataJson = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		// Convert String to HashMap -- Here jackson databinder is being used.
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(dataJson,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}

	public String getDataFromProperties(String Data) throws IOException {
		Properties prop = new Properties();
		String filePath = System.getProperty("user.dir")
				+ "//src//test//resources//globalProperties//Globaldata.properties";
		FileInputStream fis = new FileInputStream(filePath);
		prop.load(fis);
		return prop.getProperty(Data);
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot tS = (TakesScreenshot) driver;
		File sourceScreenshot = tS.getScreenshotAs(OutputType.FILE);
		String destinationPath = System.getProperty("user.dir") + File.separator + "Reports" + File.separator
				+ testCaseName + ".png";

		FileUtils.copyFile(sourceScreenshot, new File(destinationPath));
		return destinationPath;
	}

}
