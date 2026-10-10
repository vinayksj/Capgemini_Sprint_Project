package ObjRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddToCartPage {
	   WebDriver driver;

	    public AddToCartPage(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }
	    @FindBy(className="shopping_cart_link")
	    private WebElement cart;
	    
	    @FindBy(name = "checkout")
	    private WebElement checkoutB;

		public void getCart() {
			cart.click();
		}

		public void getCheckoutB() {
			checkoutB.click();
		}
		
	    
}
