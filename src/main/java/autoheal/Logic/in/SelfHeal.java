package autoheal.Logic.in;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SelfHeal {

	private WebDriver driver;

	public SelfHeal(WebDriver driver) {
		this.driver = driver;
	}

	// This method tries all the locators one by one and checks which one works for
	// click operation.
	public void click(String[] locators) {
		for (String locator : locators) {
			try {
				By byEle = LocatorUtil.getBy(locator);
				driver.findElement(byEle).click();
				System.out.println("Locator worked for click : " + locator);
				return;
			} catch (Exception e) {
				System.out.println("Locator failed for click : " + locator);
			}
		}
		throw new RuntimeException("All locators failed");
	}

	// This method tries all the locators one by one and checks which one works for
	// text input.
	public void sendText(String[] locators, String text) {
		for (String locator : locators) {
			try {
				By byEle = LocatorUtil.getBy(locator);
			    System.out.println(byEle);
				driver.findElement(byEle).sendKeys(text);
				System.out.println("Locator worked for text input : " + locator);
				return;
			} catch (Exception e) {
				System.out.println("Locator failed for text input : " + locator);
			}
		}
		throw new RuntimeException("All locators failed");
	}
}
