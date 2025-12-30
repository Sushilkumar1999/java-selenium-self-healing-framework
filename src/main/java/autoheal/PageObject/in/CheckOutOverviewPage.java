package autoheal.PageObject.in;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckOutOverviewPage {

	WebDriver driver;

	public CheckOutOverviewPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".cart_item a")
	private WebElement cartItem;

	@FindBy(id = "finish")
	private WebElement finish;

	public boolean checkifDPThereinOverview(String dP) {
		return cartItem.getText().equalsIgnoreCase(dP);
	}

	public FinalPage finish() {
		finish.click();
		FinalPage fp = new FinalPage(driver);
		return fp;
	}

}
