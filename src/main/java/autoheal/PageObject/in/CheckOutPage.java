package autoheal.PageObject.in;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckOutPage {

	WebDriver driver;

	public CheckOutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "first-name")
	private WebElement fNameL;

	@FindBy(id = "last-name")
	private WebElement lNameL;

	@FindBy(id = "postal-code")
	private WebElement postalL;

	@FindBy(id = "continue")
	private WebElement con;

	public CheckOutOverviewPage fillCheckOutForm(String firstName, String lastName, String postalCode) {
		fNameL.sendKeys(firstName);
		lNameL.sendKeys(lastName);
		postalL.sendKeys(postalCode);
		con.click();
		CheckOutOverviewPage overview = new CheckOutOverviewPage(driver);
		return overview;
	}

}
