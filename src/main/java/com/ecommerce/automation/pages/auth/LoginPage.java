package com.ecommerce.automation.pages.auth;

import com.ecommerce.automation.pages.BasePage;
import com.ecommerce.automation.pages.account.AccountPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(css = "input[name='email'], input[type='email']#email")
    private WebElement emailInput;

    @FindBy(css = "input[name='password'], input[type='password']#password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit'], .login-btn, button[class*='submit']")
    private WebElement signInButton;

    @FindBy(css = "a[href*='forgot'], .forgot-password")
    private WebElement forgotPasswordLink;

    @FindBy(css = "a[href*='register'], .create-account-link")
    private WebElement createAccountLink;

    @FindBy(css = ".social-login, a[href*='google'], button[class*='google']")
    private WebElement googleSignInButton;

    @FindBy(css = "input[type='checkbox'][name*='remember'], #remember")
    private WebElement rememberMeCheckbox;

    private static final By ERROR_MESSAGE =
            By.cssSelector(".alert-danger, .flash-error, [class*='error'], "
                    + ".bg-red-200, [class*='alert'][class*='danger']");

    private static final By SUCCESS_MESSAGE =
            By.cssSelector(".alert-success, .flash-success, [class*='success']");

    private static final By EMAIL_VALIDATION_MSG =
            By.cssSelector("input[name='email']:invalid, "
                    + ".email-error, [class*='invalid'][class*='email']");

    private static final By PASSWORD_VALIDATION_MSG =
            By.cssSelector(".password-error, [class*='invalid'][class*='password']");

    private static final By PAGE_HEADING =
            By.cssSelector("h1, h2, .page-title, [class*='title']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginPageLoaded() {
        boolean urlCorrect = getCurrentUrl().contains("login");
        boolean formVisible = isElementDisplayed(emailInput);
        log.info("🔍 Login page loaded : URL, Form ", urlCorrect, formVisible);
        return urlCorrect && formVisible;
    }

    public String getPageHeading() {
        return getText(PAGE_HEADING);
    }

    public void enterEmail(String email) {
        log.info("📧 Entering email : ", email);
        type(emailInput, email);
    }

    public void enterPassword(String password) {
        log.info("🔒 Entering password : [HIDDEN]");
        type(passwordInput, password);
    }

    public void clickSignIn() {
        log.info("🖱️ Clicking Sign In button...");
        click(signInButton);
    }

    public AccountPage loginWith(String email, String password) {
        log.info("🔐 Performing login for user : ", email);
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
        waitForUrlContains("account");
        log.info("✅ Login successful — navigated to account page");
        return new AccountPage(driver);
    }

    public void attemptLoginWith(String email, String password) {
        log.info("🔐 Attempting login for user : ", email);
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }

    public boolean isErrorMessageDisplayed() {
        boolean displayed = isElementDisplayed(ERROR_MESSAGE);
        log.info("❌ Error message displayed : ", displayed);
        return displayed;
    }

    public String getErrorMessageText() {
        String message = getText(ERROR_MESSAGE);
        return message;
    }

    public boolean isSuccessMessageDisplayed() {
        return isElementDisplayed(SUCCESS_MESSAGE);
    }

    public boolean isEmailFieldEmpty() {
        String value = getAttribute(
                By.cssSelector("input[name='email']"), "value");
        return value == null || value.isEmpty();
    }

    public boolean isForgotPasswordLinkDisplayed() {
        return isElementDisplayed(forgotPasswordLink);
    }

    public void clickForgotPassword() {
        log.info("🔑 Clicking Forgot Password...");
        click(forgotPasswordLink);
        waitForUrlContains("forgot");
    }

    public RegisterPage goToRegisterPage() {
        log.info("📝 Navigating to Register page from Login...");
        click(createAccountLink);
        return new RegisterPage(driver);
    }

    public void checkRememberMe() {
        if (isElementDisplayed(rememberMeCheckbox)) {
            if (!rememberMeCheckbox.isSelected()) {
                click(rememberMeCheckbox);
                log.info("✅ Remember Me checked");
            }
        }
    }

    public void loginWithEmailOnly(String email) {
        log.info("⚠️ Attempting login with email only (no password)");
        enterEmail(email);
        clickSignIn();
    }

    public void loginWithEmptyCredentials() {
        log.info("⚠️ Attempting login with empty credentials");
        clearField(By.cssSelector("input[name='email']"));
        clearField(By.cssSelector("input[name='password']"));
        clickSignIn();
    }
}
