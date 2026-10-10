package Ecom.SwagLabs.product;

import java.util.List;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Baseclass.BaseClass;
import GenericUtility.ExcelUtility;
import ObjRepo.LoginPage;
import ObjRepo.ProductPage;

public class ProductValid extends BaseClass {

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

    // TC-PROD-01, Verify products are visible on Product Page, Type: FUNCTIONAL
    @Test(groups = {"pos", "functional"})
    public void TC_PROD_01_verifyProductsVisible() throws Exception {
        login();
        ProductPage product = new ProductPage(driver);

        Assert.assertTrue(product.getProductCount() > 0, "Products are not displayed");
        Assert.assertFalse(product.getProductNames().isEmpty(), "Product names are not displayed");
        Assert.assertFalse(product.getProductPrices().isEmpty(), "Product prices are not displayed");

        Reporter.log("TC-PROD-01 PASSED - Products are displayed", true);
    }

    // TC-PROD-02, Verify Add to Cart and Cart Logo behavior, Type: FUNCTIONAL
    @Test(groups = {"pos", "functional"})
    public void TC_PROD_02_addProductToCart() throws Exception {
        login();

        String productName = E.readData("prod_2.xlsx", "Sheet1", 1, 0);
        ProductPage product = new ProductPage(driver);

        Assert.assertTrue(product.isProductAvailable(productName), "Product is not available: " + productName);
        Assert.assertTrue(product.addProductToCart(productName), "Product was not added to cart");
        Assert.assertEquals(product.getCartCount(), 1, "Cart count is not updated correctly");

        Reporter.log("TC-PROD-02 PASSED - Product added to cart successfully", true);
    }

    // TC-PROD-03, Verify Product Page to Cart Page using Cart Logo, Type: INTEGRATION
    @Test(groups = {"pos", "integration"})
    public void TC_PROD_03_productToCartIntegration() throws Exception {
        login();

        String productName = E.readData("prod_2.xlsx", "Sheet1", 1, 0);
        ProductPage product = new ProductPage(driver);

        Assert.assertTrue(product.addProductToCart(productName), "Product was not added to cart");
        Assert.assertEquals(product.getCartCount(), 1, "Cart count is incorrect");

        product.clickCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Cart Page is not displayed");
        Assert.assertTrue(driver.findElement(By.className("cart_item")).isDisplayed(), "Added product is not displayed in Cart");

        Reporter.log("TC-PROD-03 PASSED - Product to Cart integration successful", true);
    }

    // TC-PROD-06, Verify Product Page smoke test, Type: SMOKE
    @Test(groups = {"pos", "smoke"})
    public void TC_PROD_06_productSmoke() throws Exception {
        login();

        ProductPage product = new ProductPage(driver);
        String firstProduct = product.addFirstProductToCart();

        Assert.assertNotNull(firstProduct, "First product name is null");
        Assert.assertFalse(firstProduct.isEmpty(), "First product name is empty");
        Assert.assertEquals(product.getCartCount(), 1, "Product smoke test failed - Cart count is not 1");

        Reporter.log("TC-PROD-06 PASSED - Product smoke test successful", true);
    }

    // TC-PROD-07, Verify all product sort dropdown options, Type: SMOKE
    @Test(groups = {"pos", "smoke"})
    public void TC_PROD_07_verifySortDropdownOptions() throws Exception {
        login();

        ProductPage productPage = new ProductPage(driver);
        String[] options = {"Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)"};

        for (String option : options) {
            productPage.selectSort(option);
            String firstProduct = productPage.getFirstProductName();

            Assert.assertEquals(productPage.getSelectedSortOption(), option, option + " option was not selected");
            Reporter.log(option + " selected successfully. First Product: " + firstProduct, true);
        }
    }

    // TC-PROD-08, Verify product sorting, Type: REGRESSION
    @Test(groups = {"pos", "regression"})
    public void TC_PROD_08_sortingRegression() throws Exception {
        login();

        ProductPage product = new ProductPage(driver);
        product.selectSort("Name (A to Z)");

        Assert.assertEquals(product.getSelectedSortOption(), "Name (A to Z)", "Name A to Z option was not selected");

        List<String> names = product.getProductNames();
        Assert.assertFalse(names.isEmpty(), "Products are not displayed after sorting");

        for (int i = 0; i < names.size() - 1; i++) {
            Assert.assertTrue(names.get(i).compareToIgnoreCase(names.get(i + 1)) <= 0, "Products are not sorted in A to Z order");
        }

        product.selectSort("Price (low to high)");

        Assert.assertEquals(product.getSelectedSortOption(), "Price (low to high)", "Price low to high option was not selected");

        List<Double> prices = product.getProductPrices();
        Assert.assertFalse(prices.isEmpty(), "Product prices are not displayed");

        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i + 1), "Products are not sorted by price low to high");
        }

        Reporter.log("TC-PROD-08 PASSED - Sorting regression successful", true);
    }

    // TC-PROD-09, Verify Add to Cart after changing filter, Type: REGRESSION
    @Test(groups = {"pos", "regression"})
    public void TC_PROD_09_addAfterSorting() throws Exception {
        login();

        ProductPage product = new ProductPage(driver);
        product.selectSort("Name (Z to A)");

        String productName = E.readData("prod_2.xlsx", "Sheet1", 2, 0);

        Assert.assertTrue(product.isProductAvailable(productName), "Product is not available after sorting: " + productName);
        Assert.assertTrue(product.addProductToCart(productName), "Product was not added after sorting");
        Assert.assertEquals(product.getCartCount(), 1, "Cart count is incorrect after sorting");

        Reporter.log("TC-PROD-09 PASSED - Add to Cart after sorting successful", true);
    }
}