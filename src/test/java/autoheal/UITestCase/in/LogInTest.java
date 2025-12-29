package autoheal.UITestCase.in;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import autoheal.base.in.Base;
import autoheal.listener.in.Retry;


public class LogInTest extends Base {
	
	@Test(retryAnalyzer=Retry.class,dataProvider="getDataFromJson")
	public  void submitOrder(HashMap<String,String> input) {
		login.sendTextForUserName(input.get("userName"));
		login.sendTextForPassWord(input.get("password"));
		login.clickLogInBtn();
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
	}
	
	@DataProvider
	public Object[][] getDataFromJson() throws IOException{
		List<HashMap<String,String>> data=getDataFromJsonFile(System.getProperty("user.dir")+"//src//main//resources//LogInData.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
		
	}

}
