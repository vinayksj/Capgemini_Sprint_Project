package Ecom.SwagLabs.product;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Baseclass.BaseClass;
import GenericUtility.ExcelUtility;
import ObjRepo.ProductPage;

public class ProductInvalid extends BaseClass {

    ExcelUtility E = new ExcelUtility();

    // DATA PROVIDER, Invalid products from prod_2.xlsx -> Sheet2
    @DataProvider(name = "invalidProductData")
    public Object[][] invalidProductData() throws Exception {
        int rowCount = E.getRowCount("prod_2.xlsx", "Sheet2");
        Object[][] data = new Object[rowCount][1];

        for (int i = 0; i < rowCount; i++) {
            data[i][0] = E.readData("prod_2.xlsx", "Sheet2", i + 1, 0);
        }
        return data;
    }

    // TC-PROD-04, Verify invalid products are not available, Type: NEGATIVE / FUNCTIONAL
    @Test(dataProvider = "invalidProductData", groups = {"neg", "functional"})
    public void TC_PROD_04_verifyInvalidProduct(String productName) throws Exception {
        if (productName.trim().isEmpty()) {
            return;
        }

        login(E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 1, 0),
              E.readData("SwagLabs_Login_UserData.xlsx", "Sheet1", 1, 1));

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Login failed - Product Page not displayed");

        ProductPage productPage = new ProductPage(driver);

        Assert.assertFalse(productPage.isProductAvailable(productName), "Invalid product is unexpectedly available: " + productName);

        Reporter.log("TC-PROD-04 PASSED - Invalid product not available: " + productName, true);
    }
}