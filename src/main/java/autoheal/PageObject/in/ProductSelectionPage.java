package autoheal.PageObject.in;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import autoheal.base.in.Base;

public class ProductSelectionPage extends Base {

	// div[class='inventory_item_name ']
	// (//button[text()='Add to cart'])[4]
	WebDriver driver;

	public ProductSelectionPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".inventory_item_description")
	private List<WebElement> productsName;

	@FindBy(id = "shopping_cart_container")
	private WebElement cartL;

	private By addCartBtn = By.cssSelector(".inventory_item_description button");

	public List<WebElement> getListOfProducts() {
		return productsName;
	}

	public WebElement getDisiredProduct(String productName) {
		WebElement disiredProduct = getListOfProducts().stream()
				.filter(product -> product.findElement(By.tagName("a")).getText().equalsIgnoreCase(productName))
				.findFirst().orElse(null);
		return disiredProduct;
	}

	public CartPage selectDisiredProduct(WebElement ele) {
		ele.findElement(addCartBtn).click();
		cartL.click();
		CartPage cp = new CartPage(driver);
		return cp;
	}
}
