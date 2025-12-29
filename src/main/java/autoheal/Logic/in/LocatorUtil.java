package autoheal.Logic.in;

import org.openqa.selenium.By;

public class LocatorUtil {
	
	// This method coverts text Locator into By
	public static By getBy(String locatorText) {
		//Input: "id:user-name"
		String type = locatorText.split(":")[0].trim(); // "id"
		String value = locatorText.split(":")[1].trim(); // "user-name"

		if (type.equals("id"))
			return By.id(value);
		else if (type.equals("className"))
			return By.className(value);
		else if (type.equals("xpath"))
			return By.xpath(value);
		else if (type.equals("css"))
			return By.cssSelector(value);
		else if (type.equals("tagName"))
			return By.tagName(value);

		throw new RuntimeException("Invalid Locator Type");
		//returns By.id("user-name")
	}
}
