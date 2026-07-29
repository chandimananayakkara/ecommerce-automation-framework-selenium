package com.ecommerce.automation.pages.account;

import com.ecommerce.automation.pages.BasePage;
import com.ecommerce.automation.pages.auth.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountPage extends BasePage {

    @FindBy(css = "h1, h2, .welcome-msg, [class*='welcome'], "
            + "[class*='account-title']")
    private WebElement welcomeMessage;

    @FindBy(css = ".customer-name, [class*='user-name'], .profile-name")
    private WebElement customerName;

    @FindBy(css = "a[href*='logout'], .logout-btn, button[class*='logout']")
    private WebElement logoutLink;

    @FindBy(css = ".account-nav a, .sidebar a, [class*='account-menu'] a")
    private java.util.List<WebElement> accountNavLinks;

    private static final By FLASH_SUCCESS =
            By.cssSelector(".alert-success, .flash-message, [class*='success']");

    public AccountPage(WebDriver driver) {
        super(driver);
        log.info("📄 AccountPage initialized | URL : ", driver.getCurrentUrl());
    }

    public boolean isAccountPageLoaded() {
        boolean urlCorrect = getCurrentUrl().contains("account");
        boolean welcomeVisible = isElementDisplayed(welcomeMessage);
        log.info("🔍 AccountPage loaded : URL, Welcome", urlCorrect, welcomeVisible);
        return urlCorrect || welcomeVisible;
    }

    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }

    public String getCustomerName() {
        if (isElementDisplayed(customerName)) {
            return getText(customerName);
        }
        return "";
    }

    public boolean isWelcomeMessageContains(String expectedName) {
        String welcome = getWelcomeMessage();
        boolean contains = welcome.toLowerCase()
                .contains(expectedName.toLowerCase());
        log.info("👋 Welcome message contains : ",
                welcome, expectedName, contains);
        return contains;
    }

    public LoginPage logout() {
        log.info("🚪 Logging out...");
        scrollToElement(logoutLink);
        click(logoutLink);
        waitForUrlContains("login");
        log.info("✅ Logged out — redirected to login page");
        return new LoginPage(driver);
    }

    public String getFlashMessage() {
        if (isElementDisplayed(FLASH_SUCCESS)) {
            return getText(FLASH_SUCCESS);
        }
        return "";
    }
}
