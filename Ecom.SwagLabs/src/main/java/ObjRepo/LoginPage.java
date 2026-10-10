
package ObjRepo;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "user-name")
    private WebElement UN;

    @FindBy(name = "password")
    private WebElement PW;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement login;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public void getUN(String value) {
        UN.sendKeys(value);
    }

    public void getPW(String value) {
        PW.sendKeys(value);
    }

    public void getLogin() {
        login.click();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }
}
