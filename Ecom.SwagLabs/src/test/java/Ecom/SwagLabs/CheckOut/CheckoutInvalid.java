package Ecom.SwagLabs.CheckOut;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Baseclass.BaseClass;
import GenericUtility.ExcelUtility;
import GenericUtility.ScreenshotUtility;
import ObjRepo.LoginPage;
import ObjRepo.ProductPage;
import ObjRepo.AddToCartPage;
import ObjRepo.CheckOutPage;

public class CheckoutInvalid extends BaseClass {

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

    // DATA PROVIDER, Reads Sheet2 from Checkout_TestData.xlsx
    @DataProvider(name = "invalidCheckoutData")
    public Object[][] getInvalidCheckoutData() throws Exception {
        int rowCount = E.getRowCount("Checkout_TestData.xlsx", "Sheet2");
        Object[][] data = new Object[rowCount][3];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < 3; j++) {
                data[i][j] = E.readData("Checkout_TestData.xlsx", "Sheet2", i + 1, j);
            }
        }
        return data;
    }

    // TC-CHECKOUT-02, Verify invalid checkout data is rejected, Type: NEGATIVE / FUNCTIONAL
    @Test(dataProvider = "invalidCheckoutData", groups = {"neg", "functional"})
    public void TC_CHECKOUT_02_invalidCheckout(String firstName, String lastName, String zipCode) throws Exception {
        login();

        String productName = E.readData("prod_2.xlsx", "Sheet1", 1, 0);
        ProductPage product = new ProductPage(driver);

        Assert.assertTrue(product.addProductToCart(productName), "Product was not added to Cart");

        product.clickCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Cart Page is not displayed");

        AddToCartPage cart = new AddToCartPage(driver);
        cart.getCheckoutB();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"), "Checkout Page is not displayed");

        CheckOutPage checkout = new CheckOutPage(driver);
        checkout.getFirstname(firstName);
        checkout.getLastname(lastName);
        checkout.getPostalcode(zipCode);

        Reporter.log("Invalid Checkout Data: " + firstName + " | " + lastName + " | " + zipCode, true);

        checkout.getContinueB();

        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-step-two.html"), "Invalid checkout data was accepted");
        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-complete.html"), "Invalid checkout data resulted in purchase");

        Reporter.log("TC-CHECKOUT-02 PASSED - Invalid checkout data rejected: " + firstName + " | " + lastName + " | " + zipCode, true);
        ScreenshotUtility.takeScreenshot(driver, "checkout");
    }
}