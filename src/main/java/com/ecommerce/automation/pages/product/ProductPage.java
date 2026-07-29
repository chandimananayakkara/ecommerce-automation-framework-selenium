package com.ecommerce.automation.pages.product;

import com.ecommerce.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {
    @FindBy(css = "h1, .product-name, [class*='product-title']")
    private WebElement productTitle;

    @FindBy(css = ".price, [class*='price']:not([class*='old'])")
    private WebElement productPrice;

    @FindBy(css = "button[class*='add-to-cart'], .add-to-cart, #add-to-cart")
    private WebElement addToCartButton;

    private static final By SUCCESS_TOAST =
            By.cssSelector(".alert-success, [class*='success'], .toast");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductPageLoaded() {
        return isElementDisplayed(productTitle);
    }

    public String getProductTitle() {
        return getText(productTitle);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    public void addToCart() {
        log.info("🛒 Adding product to cart...");
        click(addToCartButton);
    }

    public boolean isAddToCartSuccessDisplayed() {
        return isElementDisplayed(SUCCESS_TOAST);
    }
}
