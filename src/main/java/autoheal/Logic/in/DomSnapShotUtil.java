package autoheal.Logic.in;

import org.openqa.selenium.WebDriver;

public class DomSnapShotUtil {
	
	public static String getNecessaryDom(WebDriver driver) {
		return driver.getPageSource();
	}

}
