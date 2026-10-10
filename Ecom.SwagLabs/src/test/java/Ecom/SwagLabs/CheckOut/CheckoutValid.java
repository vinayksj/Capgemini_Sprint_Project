package Ecom.SwagLabs.CheckOut;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Baseclass.BaseClass;
import GenericUtility.ExcelUtility;
import ObjRepo.LoginPage;
import ObjRepo.ProductPage;
import ObjRepo.AddToCartPage;
import ObjRepo.CheckOutPage;

public class CheckoutValid extends BaseClass {

    ExcelUtility E = new ExcelUtility();

    private void login() throws Exception {
        String username = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 1, 0);
        String password = E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 1, 1);
        LoginPage login = new LoginPage(driver);

        login.getUN(username);
        login.getPW(password);
        login.getLogin();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Login failed - Product Page not displayed");
    }

    // DATA PROVIDER, Reads Sheet1 from Checkout_TestData.xlsx
    @DataProvider(name = "validCheckoutData")
    public Object[][] getValidCheckoutData() throws Exception {
        int rowCount = E.getRowCount("Checkout_TestData.xlsx", "Sheet1");
        Object[][] data = new Object[rowCount][3];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < 3; j++) {
                data[i][j] = E.readData("Checkout_TestData.xlsx", "Sheet1", i + 1, j);
            }
        }
        return data;
    }

    // TC-CHECKOUT-01, Complete purchase with valid checkout details, Type: SYSTEM
    @Test(dataProvider = "validCheckoutData", groups = {"pos", "system"})
    public void TC_CHECKOUT_01_completePurchase(String firstName, String lastName, String zipCode) throws Exception {
        login();

        String productName = E.readData("prod_2.xlsx", "Sheet1", 1, 0);
        ProductPage product = new ProductPage(driver);

        Assert.assertTrue(product.addProductToCart(productName), "Product was not added to Cart");

        product.clickCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Cart Page is not displayed");
        Assert.assertTrue(driver.findElement(By.className("cart_item")).isDisplayed(), "Product is not displayed in Cart");

        Reporter.log("Product in Cart: " + productName, true);

        AddToCartPage cart = new AddToCartPage(driver);
        cart.getCheckoutB();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"), "Checkout Page is not displayed");

        CheckOutPage checkout = new CheckOutPage(driver);
        checkout.getFirstname(firstName);
        checkout.getLastname(lastName);
        checkout.getPostalcode(zipCode);

        System.out.println("Checkout Details: " + firstName + " | " + lastName + " | " + zipCode);

        checkout.getContinueB();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"), "Checkout Overview Page is not displayed");

        checkout.getFinish();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete.html"), "Purchase was not completed");
        Assert.assertTrue(driver.findElement(By.className("complete-header")).isDisplayed(), "Order confirmation message is not displayed");

        String confirmationMessage = driver.findElement(By.className("complete-header")).getText();

        Reporter.log("Purchase Successful: " + confirmationMessage, true);
        Reporter.log("Product Successfully Purchased - " + productName, true);
        Reporter.log("Checkout Data: " + firstName + " | " + lastName + " | " + zipCode, true);
        Reporter.log("TC-CHECKOUT-01 PASSED - Purchase completed successfully", true);
    }
}