package com.ecommerce.automation.pages.home;

import com.ecommerce.automation.pages.BasePage;
import com.ecommerce.automation.pages.common.HeaderComponent;
import com.ecommerce.automation.pages.product.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    private final HeaderComponent header;

    @FindBy(css = ".hero-section, .banner-section, [class*='hero']")
    private WebElement heroBanner;

    @FindBy(css = ".hero-section a, .banner-btn, [class*='shop-now']")
    private WebElement shopNowButton;

    @FindBy(css = "[class*='featured'], [class*='product-listing']")
    private WebElement featuredSection;

    @FindBy(css = ".product-card, [class*='product-item'], .card")
    private List<WebElement> productCards;

    @FindBy(css = ".category-item, nav a[href*='category'], [class*='category']")
    private List<WebElement> categoryLinks;

    @FindBy(css = "h2, h3, [class*='section-title']")
    private List<WebElement> sectionTitles;

    @FindBy(css = "input[name='email'][placeholder*='newsletter'], "
            + "input[placeholder*='Email'][type='email']")
    private WebElement newsletterEmailInput;

    @FindBy(css = "button[type='submit'][class*='subscribe'], .newsletter-btn")
    private WebElement newsletterSubscribeBtn;

    private static final By PRODUCT_PRICES =
            By.cssSelector(".price, [class*='price']:not([class*='old'])");

    private static final By PRODUCT_NAMES =
            By.cssSelector(".product-name, [class*='product-title'], h3.name");

    private static final By ADD_TO_CART_BUTTONS =
            By.cssSelector("button[class*='add-to-cart'], .add-to-cart-btn");

    private static final By WISHLIST_BUTTONS =
            By.cssSelector("button[class*='wishlist'], .wishlist-btn, [class*='add-wishlist']");

    public HomePage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver);
    }

    public HeaderComponent getHeader() {
        return header;
    }

    public boolean isHomePageLoaded() {
        try {
            waitForUrlContains("commerce.bagisto.com");
            boolean bannerVisible = isElementDisplayed(heroBanner);
            log.info("🏠 HomePage loaded : ", bannerVisible);
            return bannerVisible;
        } catch (Exception e) {
            log.warn("⚠️ Could not verify homepage load : ", e.getMessage());
            return driver.getCurrentUrl().contains("bagisto.com");
        }
    }

    public boolean isHeroBannerDisplayed() {
        return isElementDisplayed(heroBanner);
    }

    public String getTitle() {
        return getPageTitle();
    }

    public int getVisibleProductCount() {
        int count = productCards.size();
        log.info("📦 Visible product cards : ", count);
        return count;
    }

    public ProductPage clickFirstProduct() {
        log.info("🛍️ Clicking first product card...");
        if (productCards.isEmpty()) {
            throw new RuntimeException(
                    "No product cards found on homepage! Page may not have loaded."
            );
        }
        scrollToElement(productCards.get(0));
        click(productCards.get(0));
        return new ProductPage(driver);
    }

    public ProductPage clickProductByIndex(int index) {
        log.info("🛍️ Clicking product at index : ", index);
        if (index >= productCards.size()) {
            throw new IndexOutOfBoundsException(
                    String.format("Index is out of bounds.",
                            index, productCards.size())
            );
        }
        scrollToElement(productCards.get(index));
        click(productCards.get(index));
        return new ProductPage(driver);
    }

    public void clickShopNow() {
        log.info("🛒 Clicking Shop Now button...");
        click(shopNowButton);
    }

    public void subscribeNewsletter(String email) {
        log.info("📧 Subscribing to newsletter with : ", email);
        if (isElementDisplayed(newsletterEmailInput)) {
            scrollToElement(newsletterEmailInput);
            type(newsletterEmailInput, email);
            click(newsletterSubscribeBtn);
        } else {
            log.warn("⚠️ Newsletter section not found on current page view");
        }
    }

    public List<String> getProductNames() {
        List<WebElement> nameElements = getElements(PRODUCT_NAMES);
        return nameElements.stream()
                .map(WebElement::getText)
                .filter(name -> !name.isEmpty())
                .collect(java.util.stream.Collectors.toList());
    }

    public int getCategoryCount() {
        return categoryLinks.size();
    }
}
