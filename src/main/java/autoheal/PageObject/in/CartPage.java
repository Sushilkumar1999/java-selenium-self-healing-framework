package autoheal.PageObject.in;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

	WebDriver driver;

	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".cart_item a")
	private WebElement cart;

	@FindBy(id = "checkout")
	private WebElement checkOutBtn;

	public boolean checkIfDPThere(String dP) {
		boolean match = cart.getText().equalsIgnoreCase(dP);
		return match;
	}

	public CheckOutPage checkOut() {
		checkOutBtn.click();
		CheckOutPage cop = new CheckOutPage(driver);
		return cop;
	}
}
