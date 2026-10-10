package Ecom.SwagLabs.login;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Baseclass.BaseClass;
import GenericUtility.ExcelUtility;
import ObjRepo.LoginPage;

public class ValidLogin extends BaseClass {

    ExcelUtility E = new ExcelUtility();

    // TC-LOGIN-01, Verify Username, Password and Login controls are displayed, Type: FUNCTIONAL
    @Test(groups = {"pos", "functional"})
    public void TC_LOGIN_01_verifyLoginControls() throws Exception {
    	 E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 1, 0);
         E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 1, 1);
        new LoginPage(driver);

        Assert.assertTrue(driver.findElement(By.id("user-name")).isDisplayed(), "Username field is not displayed");
        Assert.assertTrue(driver.findElement(By.id("password")).isDisplayed(), "Password field is not displayed");
        Assert.assertTrue(driver.findElement(By.id("login-button")).isDisplayed(), "Login button is not displayed");

        Reporter.log("TC-LOGIN-01 PASSED - Login controls displayed", true);
    }

    // TC-LOGIN-02, Verify login with valid standard user credentials, Type: FUNCTIONAL
    @Test(groups = {"pos", "functional"})
    public void TC_LOGIN_02_validStandardUser() throws Exception {
    	 String username = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 2, 0);
         String password = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 2, 1);
        LoginPage login = new LoginPage(driver);

        login.getUN(username);
        login.getPW(password);
        login.getLogin();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Valid standard user login failed");

        Reporter.log("TC-LOGIN-02 PASSED - Standard user login successful", true);
    }

    // TC-LOGIN-03, Verify Login navigates to Product Page, Type: INTEGRATION
    @Test(groups = {"pos", "integration"})
    public void TC_LOGIN_03_loginToProductPage() throws Exception {
    	 String username = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 3, 0);
         String password = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 3, 1);
        LoginPage login = new LoginPage(driver);

        login.getUN(username);
        login.getPW(password);
        login.getLogin();

        Assert.assertTrue(driver.findElement(By.className("inventory_list")).isDisplayed(), "Product Page is not displayed");

        Reporter.log("TC-LOGIN-03 PASSED - Login navigated to Product Page", true);
    }
    
    // TC-LOGIN-08, Verify login smoke test, Type: SMOKE
    @Test(groups = {"pos", "smoke"})
    public void TC_LOGIN_08_smokeLogin() throws Exception {
    	 String username = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 4, 0);
         String password = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 4, 1);
        LoginPage login = new LoginPage(driver);

        login.getUN(username);
        login.getPW(password);
        login.getLogin();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Smoke login failed");

        Reporter.log("TC-LOGIN-08 PASSED - Smoke login successful", true);
    }

    // TC-LOGIN-09, Verify login regression after application changes, Type: REGRESSION
    @Test(groups = {"pos", "regression"})
    public void TC_LOGIN_09_regressionLogin() throws Exception {
    	 String username = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 5, 0);
         String password = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 5, 1);
        LoginPage login = new LoginPage(driver);

        login.getUN(username);
        login.getPW(password);
        login.getLogin();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Regression login failed");

        Reporter.log("TC-LOGIN-09 PASSED - Login regression successful", true);
    }
}
