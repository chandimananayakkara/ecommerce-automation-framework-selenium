package com.ecommerce.automation.pages.auth;

import com.ecommerce.automation.pages.BasePage;
import com.ecommerce.automation.pages.account.AccountPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {
    @FindBy(css = "input[name='first_name'], #first_name, input[placeholder*='First']")
    private WebElement firstNameInput;

    @FindBy(css = "input[name='last_name'], #last_name, input[placeholder*='Last']")
    private WebElement lastNameInput;

    @FindBy(css = "input[name='email'], input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input[name='phone'], input[type='tel'], input[placeholder*='Phone']")
    private WebElement phoneInput;

    @FindBy(css = "input[name='password']:not([name*='confirm']), "
            + "input[id='password'], input[placeholder*='Password']:first-of-type")
    private WebElement passwordInput;

    @FindBy(css = "input[name='password_confirmation'], "
            + "input[name='confirm_password'], input[placeholder*='Confirm']")
    private WebElement confirmPasswordInput;

    @FindBy(css = "input[type='checkbox'][name*='agree'], "
            + "input[type='checkbox'][name*='terms'], #agree")
    private WebElement termsCheckbox;

    @FindBy(css = "button[type='submit'], .register-btn, button[class*='submit']")
    private WebElement registerButton;

    @FindBy(css = "a[href*='login'], .login-link, [class*='sign-in']")
    private WebElement signInLink;

    private static final By SUCCESS_MESSAGE =
            By.cssSelector(".alert-success, .flash-success, [class*='success']");

    private static final By ERROR_MESSAGE =
            By.cssSelector(".alert-danger, .flash-error, [class*='error']");

    private static final By FIRST_NAME_ERROR =
            By.cssSelector("[class*='error'][class*='first'], "
                    + "input[name='first_name'] + span.error, "
                    + "#first_name ~ .invalid-feedback");

    private static final By LAST_NAME_ERROR =
            By.cssSelector("[class*='error'][class*='last'], "
                    + "input[name='last_name'] + span.error");

    private static final By EMAIL_ERROR =
            By.cssSelector("input[name='email'] + span.error, "
                    + "[class*='email'][class*='error']");

    private static final By PASSWORD_ERROR =
            By.cssSelector("input[name='password'] + span.error, "
                    + "[class*='password'][class*='error']:not([class*='confirm'])");

    private static final By CONFIRM_PASSWORD_ERROR =
            By.cssSelector("input[name='password_confirmation'] + span.error, "
                    + "[class*='confirm'][class*='error']");

    private static final By PAGE_HEADING =
            By.cssSelector("h1, h2, .page-title");

    public RegisterPage(WebDriver driver) {
        super(driver);
        log.info("📄 RegisterPage initialized | URL : ", driver.getCurrentUrl());
    }

    public boolean isRegisterPageLoaded() {
        boolean urlCorrect = getCurrentUrl().contains("register");
        boolean formVisible = isElementDisplayed(firstNameInput);
        log.info("🔍 Register page loaded: URL, Form", urlCorrect, formVisible);
        return urlCorrect && formVisible;
    }

    public String getPageHeading() {
        return getText(PAGE_HEADING);
    }

    public void enterFirstName(String firstName) {
        log.info("👤 Entering first name : ", firstName);
        type(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        log.info("👤 Entering last name : ", lastName);
        type(lastNameInput, lastName);
    }

    public void enterEmail(String email) {
        log.info("📧 Entering email : ", email);
        type(emailInput, email);
    }

    public void enterPhone(String phone) {
        log.info("📞 Entering phone : ", phone);
        if (isElementDisplayed(phoneInput)) {
            type(phoneInput, phone);
        }
    }

    public void enterPassword(String password) {
        log.info("🔒 Entering password : [HIDDEN]");
        type(passwordInput, password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        log.info("🔒 Entering confirm password : [HIDDEN]");
        type(confirmPasswordInput, confirmPassword);
    }

    public void checkTermsAndConditions() {
        log.info("☑️ Checking Terms and Conditions...");
        if (isElementDisplayed(termsCheckbox)
                && !termsCheckbox.isSelected()) {
            click(termsCheckbox);
        }
    }

    public void clickRegisterButton() {
        log.info("🖱️ Clicking Register button...");
        scrollToElement(registerButton);
        click(registerButton);
    }
    public AccountPage registerWith(String firstName, String lastName,
                                    String email, String password,
                                    String confirmPassword) {

        log.info("📝 Registering new user : ", firstName, lastName, email);

        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        checkTermsAndConditions();
        clickRegisterButton();

        waitForUrlContains("account");
        log.info("✅ Registration successful!");
        return new AccountPage(driver);
    }

   public void attemptRegistrationWith(String firstName, String lastName,
                                        String email, String password,
                                        String confirmPassword) {
        log.info("⚠️ Attempting registration (negative test) : ", email);
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        checkTermsAndConditions();
        clickRegisterButton();
    }

    public AccountPage quickRegister(String firstName, String lastName,
                                     String email, String password) {
        return registerWith(firstName, lastName, email, password, password);
    }
    public boolean isSuccessMessageDisplayed() {
        return isElementDisplayed(SUCCESS_MESSAGE);
    }

    public String getSuccessMessageText() {
        return getText(SUCCESS_MESSAGE);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(ERROR_MESSAGE);
    }

    public String getErrorMessageText() {
        return getText(ERROR_MESSAGE);
    }

    public boolean isFirstNameErrorDisplayed() {
        return isElementDisplayed(FIRST_NAME_ERROR);
    }

    public boolean isLastNameErrorDisplayed() {
        return isElementDisplayed(LAST_NAME_ERROR);
    }

    public boolean isEmailErrorDisplayed() {
        return isElementDisplayed(EMAIL_ERROR);
    }

    public String getEmailErrorText() {
        return isEmailErrorDisplayed() ? getText(EMAIL_ERROR) : "";
    }

    public boolean isPasswordErrorDisplayed() {
        return isElementDisplayed(PASSWORD_ERROR);
    }

    public boolean isConfirmPasswordErrorDisplayed() {
        return isElementDisplayed(CONFIRM_PASSWORD_ERROR);
    }

    public boolean hasAnyValidationError() {
        return isFirstNameErrorDisplayed()
                || isLastNameErrorDisplayed()
                || isEmailErrorDisplayed()
                || isPasswordErrorDisplayed()
                || isConfirmPasswordErrorDisplayed()
                || isErrorMessageDisplayed();
    }

    public LoginPage goToLoginPage() {
        log.info("🔗 Going to Login page from Register page...");
        click(signInLink);
        return new LoginPage(driver);
    }
}
