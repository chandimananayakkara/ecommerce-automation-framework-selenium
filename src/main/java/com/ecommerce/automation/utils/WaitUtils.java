package com.ecommerce.automation.utils;

import com.ecommerce.automation.driver.DriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WaitUtils {
    private static final Logger log = (Logger) LoggerFactory.getLogger(WaitUtils.class);

    private WaitUtils() {}

     public static WebElement waitForVisible(By locator) {
        log.debug("⏳ Waiting for element to be visible : ", locator);
        return DriverManager.getWait()
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(By locator) {
        log.debug("⏳ Waiting for element to be clickable : ", locator);
        return DriverManager.getWait()
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForClickable(WebElement element) {
        log.debug("⏳ Waiting for WebElement to be clickable...");
        return DriverManager.getWait()
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean waitForInvisible(By locator) {
        log.debug("⏳ Waiting for element to disappear : ", locator);
        return DriverManager.getWait()
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean waitForText(By locator, String text) {
        log.debug("⏳ Waiting for text '{}' in element : ", text, locator);
        return DriverManager.getWait()
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static boolean waitForUrlContains(String urlFragment) {
        log.debug("⏳ Waiting for URL to contain : ", urlFragment);
        return DriverManager.getWait()
                .until(ExpectedConditions.urlContains(urlFragment));
    }


    public static boolean waitForTitleContains(String titleFragment) {
        log.debug("⏳ Waiting for title to contain : ", titleFragment);
        return DriverManager.getWait()
                .until(ExpectedConditions.titleContains(titleFragment));
    }

    public static WebElement waitForVisibleWithTimeout(By locator, int timeoutSeconds) {
        log.debug("⏳ Waiting {}s for element: {}", timeoutSeconds, locator);
        WebDriverWait customWait = new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(timeoutSeconds)
        );
        return customWait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public static WebElement waitForPresence(By locator) {
        log.debug("⏳ Waiting for element presence in DOM : ", locator);
        return DriverManager.getWait()
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
