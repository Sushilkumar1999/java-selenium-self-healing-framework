package autoheal.UITestCase.in;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import autoheal.PageObject.in.CartPage;
import autoheal.PageObject.in.CheckOutOverviewPage;
import autoheal.PageObject.in.CheckOutPage;
import autoheal.PageObject.in.FinalPage;
import autoheal.PageObject.in.ProductSelectionPage;
import autoheal.base.in.Base;
import autoheal.listener.in.Retry;

public class UITest extends Base {

	@Test(retryAnalyzer = Retry.class, dataProvider = "getDataFromJson", enabled = true)
	public void LoginTest(HashMap<String, String> input) throws IOException {
		login.sendTextForUserName(input.get("userName"));
		login.sendTextForPassWord(input.get("password"));
		login.clickLogInBtn();
		Assert.assertEquals(driver.getCurrentUrl(), getDataFromProperties("InventoryURL"));
	}

	@DataProvider
	public Object[][] getDataFromJson() throws IOException {
		List<HashMap<String, String>> data = getDataFromJsonFile(
				System.getProperty("user.dir") + "//src//main//resources//LogInData.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

	@Test
	public void submitOrder() throws IOException {
		// "Here the userName and password being read from properties file"
		ProductSelectionPage ps = login.aLogin(getDataFromProperties("UserName"), getDataFromProperties("Password"));
		Assert.assertEquals(driver.getCurrentUrl(), getDataFromProperties("InventoryURL"));
		WebElement disiredProduct = ps.getDisiredProduct(getDataFromProperties("DProductName"));
		CartPage cp = ps.selectDisiredProduct(disiredProduct);
		Assert.assertTrue(cp.checkIfDPThere(getDataFromProperties("DProductName")));
		CheckOutPage cop = cp.checkOut();
		CheckOutOverviewPage overview = cop.fillCheckOutForm(getDataFromProperties("firstName"),
				getDataFromProperties("lastName"), getDataFromProperties("postalCode"));
		Assert.assertTrue(overview.checkifDPThereinOverview(getDataFromProperties("DProductName")));
		FinalPage fp = overview.finish();
		Assert.assertEquals(fp.getOrderConfirmText(), getDataFromProperties("OrderConfirmText"));
	}

}
