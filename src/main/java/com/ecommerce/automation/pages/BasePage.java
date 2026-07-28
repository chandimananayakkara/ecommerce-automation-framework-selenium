package com.ecommerce.automation.pages;

import com.ecommerce.automation.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

public class BasePage {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final WebDriver driver;

   protected final Actions actions;

   public BasePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);

        PageFactory.initElements(driver, this);

        log.debug("🏗️ Page initialized : ", this.getClass().getSimpleName());
    }

   protected void click(By locator) {
        log.debug("🖱️ Clicking element: {}", locator);
        WaitUtils.waitForClickable(locator).click();
    }

    protected void click(WebElement element) {
        log.debug("🖱️ Clicking WebElement: {}", element.toString());
        WaitUtils.waitForClickable(element).click();
    }

      protected void type(By locator, String text) {
        log.debug("⌨️ Typing into : ", text, locator);
        WebElement element = WaitUtils.waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

   protected void type(WebElement element, String text) {
        log.debug("⌨️ Typing into WebElement", text);
        WaitUtils.waitForClickable(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        String text = WaitUtils.waitForVisible(locator).getText().trim();
        log.debug("📖 Got text from : ", text, locator);
        return text;
    }

    protected String getText(WebElement element) {
        WaitUtils.waitForClickable(element);
        return element.getText().trim();
    }

    protected String getAttribute(By locator, String attribute) {
        return WaitUtils.waitForVisible(locator).getAttribute(attribute);
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            boolean displayed = !elements.isEmpty() && elements.get(0).isDisplayed();
            log.debug("👁️ Element displayed : ", locator, displayed);
            return displayed;
        } catch (Exception e) {
            log.debug("👁️ Element not found : ", locator);
            return false;
        }
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

     protected void selectByVisibleText(By locator, String text) {
        log.debug("📋 Selecting from dropdown : ", text, locator);
        WebElement selectElement = WaitUtils.waitForVisible(locator);
        new Select(selectElement).selectByVisibleText(text);
    }

   protected void selectByValue(By locator, String value) {
        log.debug("📋 Selecting value from dropdown : ", value, locator);
        WebElement selectElement = WaitUtils.waitForVisible(locator);
        new Select(selectElement).selectByValue(value);
    }

    protected void scrollToElement(WebElement element) {
        log.debug("📜 Scrolling to element...");
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                        element);

        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
    }

    protected void jsClick(WebElement element) {
        log.debug("🔮 JavaScript clicking element...");
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

   protected void scrollToTop() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, 0);");
    }

   protected void hoverOver(WebElement element) {
        log.debug("🖱️ Hovering over element...");
        actions.moveToElement(element).perform();
    }

     protected void hoverOver(By locator) {
        WebElement element = WaitUtils.waitForVisible(locator);
        actions.moveToElement(element).perform();
    }

     protected void pressEnter(By locator) {
        WaitUtils.waitForVisible(locator).sendKeys(Keys.ENTER);
    }

    protected void pressEnter(WebElement element) {
        element.sendKeys(Keys.ENTER);
    }

     protected void clearField(By locator) {
        WaitUtils.waitForVisible(locator).clear();
    }

   public String getPageTitle() {
        return driver.getTitle();
    }
 public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

   protected void waitForUrlContains(String urlFragment) {
        WaitUtils.waitForUrlContains(urlFragment);
    }

   protected void waitForTitleContains(String titleFragment) {
        WaitUtils.waitForTitleContains(titleFragment);
    }

     protected List<WebElement> getElements(By locator) {
        WaitUtils.waitForVisible(locator);
        return driver.findElements(locator);
    }

     protected int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }

     protected void waitForLoaderToDisappear(By loaderLocator) {
        log.debug("⏳ Waiting for loader to disappear...");
        try {
            // First check if loader is even present
            if (!driver.findElements(loaderLocator).isEmpty()) {
                WaitUtils.waitForInvisible(loaderLocator);
            }
        } catch (Exception e) {
            log.debug("Loader not found — page likely already loaded.");
        }
    }

    protected boolean elementContainsText(By locator, String expectedText) {
        String actualText = getText(locator);
        return actualText.contains(expectedText);
    }
}
