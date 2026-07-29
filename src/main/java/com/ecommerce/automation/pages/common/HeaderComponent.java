package com.ecommerce.automation.pages.common;

import com.ecommerce.automation.pages.BasePage;
import com.ecommerce.automation.pages.auth.LoginPage;
import com.ecommerce.automation.pages.auth.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BasePage {

    @FindBy(css = "a.logo")
    private WebElement logoLink;

    @FindBy(css = "input[name='query'], input[placeholder*='Search']")
    private WebElement searchInput;

    @FindBy(css = "button[type='submit'], .search-btn")
    private WebElement searchButton;

    @FindBy(css = "a[href*='cart'], .cart-icon, #cart")
    private WebElement cartIcon;

    @FindBy(css = ".cart-count, .badge, span[class*='count']")
    private WebElement cartCountBadge;

    @FindBy(css = ".account-dropdown, a[href*='account'], .profile-icon")
    private WebElement accountMenuTrigger;

    @FindBy(css = "a[href*='login'], .login-link")
    private WebElement signInLink;

    @FindBy(css = "a[href*='register'], .register-link")
    private WebElement signUpLink;

    @FindBy(css = "a[href*='wishlist'], .wishlist-icon")
    private WebElement wishlistIcon;

    @FindBy(css = "a[href*='compare'], .compare-icon")
    private WebElement compareIcon;

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public LoginPage goToLoginPage() {
        log.info("🔗 Navigating to Login page via header...");
        click(signInLink);
        return new LoginPage(driver);
    }

    public RegisterPage goToRegisterPage() {
        log.info("🔗 Navigating to Register page via header...");
        click(signUpLink);
        return new RegisterPage(driver);
    }

    public void searchFor(String keyword) {
        log.info("🔍 Searching for: '{}'", keyword);
        type(searchInput, keyword);
        pressEnter(searchInput);
    }

    public void openCart() {
        log.info("🛒 Opening cart...");
        click(cartIcon);
        waitForUrlContains("cart");
    }

    public int getCartItemCount() {
        try {
            String countText = getText(cartCountBadge);
            return Integer.parseInt(countText.trim());
        } catch (Exception e) {
            log.debug("Cart badge not visible — cart may be empty");
            return 0;
        }
    }

    public void clickLogo() {
        log.info("🏠 Clicking logo to go home...");
        click(logoLink);
        waitForUrlContains("commerce.bagisto.com");
    }

    public void openWishlist() {
        log.info("❤️ Opening wishlist...");
        click(wishlistIcon);
        waitForUrlContains("wishlist");
    }

    public boolean isUserLoggedIn() {
        return isElementDisplayed(accountMenuTrigger)
                && !isElementDisplayed(signInLink);
    }

    public void logout() {
        log.info("🚪 Logging out...");
        hoverOver(accountMenuTrigger);


        click(org.openqa.selenium.By.cssSelector(
                "a[href*='logout'], .logout-link, li:contains('Logout')"));

        log.info("✅ Logged out successfully");
    }
}
