package autoheal.base.in;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import autoheal.PageObject.in.LogInPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	public WebDriver driver;
	public LogInPage login;

	public WebDriver initDriver(String browserName) {
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		return driver;
	}
	
	@Parameters("browserName")
	@BeforeMethod(alwaysRun=true)
	public LogInPage launchApplication(String browserName) throws IOException {
		driver = initDriver(browserName);
		login= new LogInPage(driver);
		login.goTo(getDataFromProperties("URL"));
		return login;
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	public List<HashMap<String, String>> getDataFromJsonFile(String filePath) throws IOException {
		// Read JSON to String
		String dataJson=FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		// Convert String to HashMap -- Here jackson databinder is being used.
		ObjectMapper mapper= new ObjectMapper();
		List<HashMap<String,String>>data=mapper.readValue(dataJson, new TypeReference<List<HashMap<String,String>>>(){});
		return data;
	}
	
	public String getDataFromProperties(String Data) throws IOException {
		Properties prop=new Properties();
		String filePath=System.getProperty("user.dir")+"//src//test//resources//globalProperties//Globaldata.properties";
		FileInputStream fis=new FileInputStream(filePath);
		prop.load(fis);
		return prop.getProperty(Data);
	}
	
	public  String getScreenshot(String testCaseName,WebDriver driver) throws IOException {
		TakesScreenshot tS = (TakesScreenshot) driver;
		File sourceScreenshot = tS.getScreenshotAs(OutputType.FILE);
		File destinationScreenshot = new File(System.getProperty("user.dir") + "//Reports" + testCaseName + ".png");
		FileUtils.copyFile(sourceScreenshot, destinationScreenshot);
		return System.getProperty("user.dir") + "//Reports" + testCaseName + ".png";
	}

}
