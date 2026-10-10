package Ecom.SwagLabs.AddToCart;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Baseclass.BaseClass;
import GenericUtility.ExcelUtility;
import ObjRepo.AddToCartPage;
import ObjRepo.LoginPage;
import ObjRepo.ProductPage;

public class AddToCart extends BaseClass {

    ExcelUtility E = new ExcelUtility();

    private void login() throws Exception {
        E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 1, 0);
        E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 1, 1);
        new LoginPage(driver);


        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Login failed - Product Page not displayed");
    }

    // TC-PUR-01, Verify product is added to Cart, Type: FUNCTIONAL
    @Test(groups = {"pos", "functional"})
    public void TC_PUR_01_verifyProductAddedToCart() throws Exception {
        login();

        String productName = E.readData("prod_2.xlsx", "Sheet1", 1, 0);
        ProductPage product = new ProductPage(driver);

        Assert.assertTrue(product.addProductToCart(productName), "Product was not added to Cart");
        Assert.assertEquals(product.getCartCount(), 1, "Cart count is not 1");

        product.clickCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Cart Page is not displayed");
        Assert.assertTrue(driver.findElement(By.className("cart_item")).isDisplayed(), "Product is not displayed in Cart");

        Reporter.log("TC-PUR-01 PASSED - Product verified in Cart: " + productName, true);
    }

    // TC-PUR-02, Verify Cart to Checkout navigation, Type: INTEGRATION
    @Test(groups = {"pos", "integration"})
    public void TC_PUR_02_cartToCheckout() throws Exception {
        login();

        String productName = E.readData("prod_2.xlsx", "Sheet1", 1, 0);
        ProductPage product = new ProductPage(driver);

        Assert.assertTrue(product.addProductToCart(productName), "Product was not added to Cart");
        Assert.assertEquals(product.getCartCount(), 1, "Cart count is not 1");

        product.clickCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Cart Page is not displayed");
        Assert.assertTrue(driver.findElement(By.className("cart_item")).isDisplayed(), "Product is not displayed in Cart");

        new AddToCartPage(driver).getCheckoutB();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"), "Checkout Page is not displayed");

        Reporter.log("TC-PUR-02 PASSED - Cart to Checkout integration successful: " + productName, true);
    }
}