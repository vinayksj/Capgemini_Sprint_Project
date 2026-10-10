package ObjRepo;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ProductPage {

    private WebDriver driver;

    //  constructor
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // SORT DROPDOWN
    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    // ALL PRODUCT CARDS
    @FindBy(className = "inventory_item")
    private List<WebElement> allProducts;

    // PRODUCT NAMES
    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    // PRODUCT PRICES
    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    // ADD TO CART BUTTONS
    @FindBy(className = "btn_inventory")
    private List<WebElement> addToCartButtons;

    // CART ICON
    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    // CART BADGE
    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    // VERIFY PRODUCTS ARE DISPLAYED
    public boolean areProductsDisplayed() {

        return allProducts.size() > 0;
    }

    // GET PRODUCT COUNT
    public int getProductCount() {

        return allProducts.size();
    }


    // GET PRODUCT NAMES

    public List<String> getProductNames() {

        List<String> names = new ArrayList<>();

        for (WebElement element : productNames) {

            names.add(element.getText());
        }

        return names;
    }

    // GET PRODUCT PRICES
    public List<Double> getProductPrices() {

        List<Double> prices = new ArrayList<>();

        for (WebElement element : productPrices) {

            String price = element.getText();

            price = price.replace("$", "").trim();

            prices.add(Double.parseDouble(price));
        }

        return prices;
    }

    // SELECT SORT
    public void selectSort(String option) {

        Select select = new Select(sortDropdown);

        select.selectByVisibleText(option);
    }

    // SELECT FIRST OPTION
    public void selectFirstSortOption() {

        Select select = new Select(sortDropdown);

        select.selectByIndex(0);
    }

    // GET SELECTED SORT
    public String getSelectedSortOption() {

        Select select = new Select(sortDropdown);
        return select.getFirstSelectedOption().getText();
    }

    // GET FIRST PRODUCT
    public String getFirstProductName() {

        return productNames.get(0).getText();
    }

    // CHECK PRODUCT AVAILABLE
    public boolean isProductAvailable(String productName) {
        for (WebElement element : productNames) {
            if (element.getText().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }
 
    // ADD PRODUCT TO CART
    public boolean addProductToCart(String productName) {

        for (int i = 0; i < productNames.size(); i++) {

            if (productNames.get(i)
                    .getText()
                    .equalsIgnoreCase(productName)) {

                addToCartButtons.get(i).click();

                return true;
            }
        }

        return false;
    }


   
    // ADD FIRST PRODUCT TO CART
    public String addFirstProductToCart() {

        String productName =
                productNames.get(0).getText();

        addToCartButtons.get(0).click();

        return productName;
    }


   
    // GET CART COUNT
    public int getCartCount() {

        if (cartBadge.isDisplayed()) {

            return Integer.parseInt(
                    cartBadge.getText()
            );
        }

        return 0;
    }
    
    //click cart
    public void clickCart() {

        cartIcon.click();
    }
}