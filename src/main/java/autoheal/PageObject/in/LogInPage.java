package autoheal.PageObject.in;

import org.openqa.selenium.WebDriver;

import autoheal.Logic.in.SelfHeal;

public class LogInPage {

	private SelfHeal sh;
	private WebDriver driver;

	public LogInPage(WebDriver driver) {
		this.driver = driver;
		this.sh = new SelfHeal(driver);
	}

	// Possible locators for LoginButton in LoginPage.
	String[] userNameLocator = { "id:user-namee", "xpath://input[@placeholder='Usernam']" };
	String[] passWord = { "id:passwor", "xpath://input[@placeholder='Passwordd']" };
	// In LoginButtonLocator the primary locator (id:login-buttonn) is deliberately
	// broken.
	// If it fails, the framework automatically retries the secondary
	// locator("xpath://input[@value='Login']").
	// and proceeds with execution instead of failing the test.
	String[] LoginButtonLocator = { "id:login-buttonn", "xpath://input[@value='Loginn']" };

	public void goTo(String URL) {
		driver.get(URL);
	}

	// This method calls sendText method which is implemented in selfHeal class.
	public void sendTextForUserName(String text) {
		sh.sendText(userNameLocator, text, "userName");
	}

	// This method calls sendText method which is implemented in selfHeal class.
	public void sendTextForPassWord(String text) {
		sh.sendText(passWord, text, "password");
	}

	// This method calls click method which is implemented in SelfHeal class.
	public void clickLogInBtn() {
		sh.click(LoginButtonLocator,"loginBtn");
	}

	public ProductSelectionPage aLogin(String userName, String password) {
		sendTextForUserName(userName);
		sendTextForPassWord(password);
		clickLogInBtn();
		ProductSelectionPage ps = new ProductSelectionPage(driver);
		return ps;
	}

}
