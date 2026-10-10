package ObjRepo;

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
	    
	    @FindBy(name="firstName")
	    private WebElement firstname;
	    
	    @FindBy(name="lastName")
	    private WebElement lastname;
	    
	    @FindBy(name="postalCode")
	    private WebElement postalcode;
	    
	    @FindBy(name="continue")
	    private WebElement continueB;
	    
	    @FindBy(name="finish")
	    private WebElement finish;

		public void getFinish() {
			finish.click();
		}

		public void getFirstname(String value) {
			firstname.sendKeys(value);
		}

		public void getLastname(String value) {
			lastname.sendKeys(value);
		}

		public void getPostalcode(String value) {
			postalcode.sendKeys(value);
		}

		public void getContinueB() {
			continueB.click();
		}
	    
}
