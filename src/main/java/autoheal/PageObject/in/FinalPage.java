package autoheal.PageObject.in;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FinalPage {

	WebDriver driver;

	public FinalPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".complete-header")
	private WebElement comp;

	public String getOrderConfirmText() {
		return comp.getText();
	}

}
