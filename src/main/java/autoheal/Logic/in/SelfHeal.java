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
	public void click(String[] locators, String elementName) {

		String healedLocator = HealingStore.getBestLocator(elementName);
		System.out.println(healedLocator);
		if (healedLocator != null) {
			try {
				System.out.println("I was here");
				By byEle = LocatorUtil.getBy(healedLocator);
				driver.findElement(byEle).click();
				HealingStore.saveLocator(elementName, healedLocator);
				HealingContext.setMsg("Healed Locator worked for  " + elementName + " is " + healedLocator);
				System.out.println("Healed Locator Worked for  " + elementName + " is " + healedLocator);
				return;
			} catch (Exception e) {
				HealingStore.decreaseScore(elementName, healedLocator);
				HealingContext.setMsg("Healed Locator failed for  " + elementName + " is " + healedLocator +"Hence locator score reduced");
				System.out.println("Healed Locator failed for " + elementName + " is " + healedLocator +"Hence locator score reduced");
			}
		}

		for (String locator : locators) {
			try {
				By byEle = LocatorUtil.getBy(locator);
				driver.findElement(byEle).click();
				HealingStore.saveLocator(elementName, locator);
				HealingContext.setMsg("Fallback Locator worked for  " + elementName + " is " + locator);
				System.out.println("Fallback Locator worked for  " + elementName + " is " + locator);
				return;
			} catch (Exception e) {
				HealingContext.setMsg("Fallback Locator failed for " + elementName + " is " + locator);
				System.out.println("Fallback Locator failed for  : " + elementName + " is " + locator);
			}
		}
		throw new RuntimeException("All locators failed");
	}

	// This method tries all the locators one by one and checks which one works for
	// text input.
	public void sendText(String[] locators, String text, String elementName) {

		String healedLocator = HealingStore.getBestLocator(elementName);
		System.out.println("Found healed locator : " + healedLocator);
		if (healedLocator != null) {
			try {
				By byEle = LocatorUtil.getBy(healedLocator);
				driver.findElement(byEle).sendKeys(text);
				HealingStore.saveLocator(elementName, healedLocator);
				HealingContext.setMsg("Healed Locator worked for " + elementName + " is " + healedLocator);
				System.out.println("Healed Locator worked for " + elementName + " is " + healedLocator);
				return;
			} catch (Exception e) {
				HealingStore.decreaseScore(elementName, healedLocator);
				HealingContext.setMsg("Healed Locator failed for  " + elementName + " is " + healedLocator +"Hence locator score reduced");
				System.out.println("Healed Locator failed for " + elementName + " is " + healedLocator);
			}
		}
		for (String locator : locators) {
			try {
				By byEle = LocatorUtil.getBy(locator);
				System.out.println(byEle);
				driver.findElement(byEle).sendKeys(text);
				HealingStore.saveLocator(elementName, locator);
				HealingContext.setMsg("Fallback Locator worked for  " + elementName + " is " + locator);
				System.out.println("Fallback Locator worked for  " + elementName + " is " + locator);
				return;
			} catch (Exception e) {
				HealingContext.setMsg("Fallback Locator failed for  " + elementName + " is " + locator);
				System.out.println("Fallback Locator failed for  " + elementName + " is " + locator);
			}
		}
		throw new RuntimeException("All locators failed");
	}
}
