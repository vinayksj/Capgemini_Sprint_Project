package Ecom.SwagLabs.login;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Baseclass.BaseClass;
import GenericUtility.ExcelUtility;
import GenericUtility.ScreenshotUtility;
import ObjRepo.LoginPage;

public class InvalidLogin extends BaseClass {

    ExcelUtility E = new ExcelUtility();

    
    // TC-LOGIN-04, Verify login with invalid username, Type: NEGATIVE
    @Test(groups = {"neg", "functional"})
    public void TC_LOGIN_04_invalidUsername() throws Exception {
    	 String username = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet2", 1, 0);
         String password = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet2", 1, 1);
        LoginPage login = new LoginPage(driver);

        login.getUN(username);
        login.getPW(password);
        login.getLogin();

        Assert.assertFalse(driver.getCurrentUrl().contains("inventory.html"), "Invalid username was accepted");
        Assert.assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed(), "Error message not displayed");

        Reporter.log("TC-LOGIN-04 PASSED - Invalid username rejected", true);
    }

    // TC-LOGIN-05, Verify login with invalid password, Type: NEGATIVE
    @Test(groups = {"neg", "functional"})
    public void TC_LOGIN_05_invalidPassword() throws Exception {
    	 String username = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet2", 2, 0);
         String password = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet2", 2, 1);
        LoginPage login = new LoginPage(driver);

        login.getUN(username);
        login.getPW(password);
        login.getLogin();

        Assert.assertFalse(driver.getCurrentUrl().contains("inventory.html"), "Invalid password was accepted");
        Assert.assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed(), "Error message not displayed");

        Reporter.log("TC-LOGIN-05 PASSED - Invalid password rejected", true);
    }

    
    // TC-LOGIN-06, Verify login with blank username, Type: NEGATIVE
    @Test(groups = {"neg", "functional"})
    public void TC_LOGIN_06_blankUsername() throws Exception {
    	 String username = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet2", 3, 0);
         String password = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet2", 3, 1);
        LoginPage login = new LoginPage(driver);

        login.getUN(username);
        login.getPW(password);
        login.getLogin();

        Assert.assertFalse(driver.getCurrentUrl().contains("inventory.html"), "Blank username was accepted");
        Assert.assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed(), "Username validation message not displayed");

        Reporter.log("TC-LOGIN-06 PASSED - Blank username rejected", true);
        ScreenshotUtility.takeScreenshot(driver,"invalidlogin");
    }
    
    // TC-LOGIN-07, Verify login with blank password, Type: NEGATIVE
    @Test(groups = {"neg", "functional"})
    public void TC_LOGIN_07_blankPassword() throws Exception {
    	 String username = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet2", 4, 0);
         String password = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet2", 4, 1);
        LoginPage login = new LoginPage(driver);

        login.getUN(username);
        login.getPW(password);
        login.getLogin();

        Assert.assertFalse(driver.getCurrentUrl().contains("inventory.html"), "Blank password was accepted");
        Assert.assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed(), "Password validation message not displayed");

        Reporter.log("TC-LOGIN-07 PASSED - Blank password rejected", true);
        
       

        Reporter.log("Invalid login error displayed successfully", true);
        Reporter.log("Screenshot saved at ./ss/saucedemo4.png", true);
    }
}
