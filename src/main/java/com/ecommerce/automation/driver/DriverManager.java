package com.ecommerce.automation.driver;

import com.ecommerce.automation.config.ConfigManager;
import com.ecommerce.automation.constants.AppConstants;
import org.slf4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class DriverManager {
    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);

    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    private static final ThreadLocal<WebDriverWait> threadLocalWait = new ThreadLocal<>();

    private static final ConfigManager config = ConfigManager.getInstance();

    private DriverManager() {
    }

    public static void initDriver(String browser) {
        if (threadLocalDriver.get() != null) {
            log.warn("⚠️ Driver already exists for thread...",
                    Thread.currentThread().getName());
            quitDriver();
        }

        WebDriver driver = DriverFactory.createDriver(browser);

        configureTimeouts(driver);

        if (!config.isHeadless()) {
            driver.manage().window().maximize();
            log.info("🪟 Browser window maximized");
        }

        threadLocalDriver.set(driver);

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(config.getExplicitWait())
        );
        threadLocalWait.set(wait);

        log.info("✅ WebDriver initialized successfully for thread... ",
                Thread.currentThread().getName(), browser.toUpperCase());
    }

    private static void configureTimeouts(WebDriver driver) {
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));

        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));

        driver.manage().timeouts()
                .scriptTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));

        log.info("⏱️ Timeouts configured",
                config.getImplicitWait(), config.getPageLoadTimeout());
    }

    public static WebDriver getDriver() {
        WebDriver driver = threadLocalDriver.get();
        if (driver == null) {
            log.error("❌ WebDriver not initialized for thread...",
                    Thread.currentThread().getName());
            throw new RuntimeException(
                    "WebDriver is NULL! Make sure initDriver() is called in @BeforeMethod. " +
                            "Thread: " + Thread.currentThread().getName()
            );
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        WebDriverWait wait = threadLocalWait.get();
        if (wait == null) {
            log.warn("⚠️ WebDriverWait not initialized! Creating with default timeout...");
            wait = new WebDriverWait(
                    getDriver(),
                    Duration.ofSeconds(AppConstants.DEFAULT_TIMEOUT)
            );
            threadLocalWait.set(wait);
        }
        return wait;
    }

    public static void quitDriver() {
        WebDriver driver = threadLocalDriver.get();

        if (driver != null) {
            try {
                driver.quit();
                log.info("✅ WebDriver quit successfully for thread...",
                        Thread.currentThread().getName());
            } catch (Exception e) {
                log.error("❌ Error while quitting WebDriver : ", e.getMessage());
            } finally {
                threadLocalDriver.remove();
                threadLocalWait.remove();
                log.info("🧹 ThreadLocal cleaned up for thread :",
                        Thread.currentThread().getName());
            }
        } else {
            log.warn("⚠️ quitDriver() called but driver was NULL for thread : ",
                    Thread.currentThread().getName());
        }
    }

    public static boolean isDriverInitialized() {
        return threadLocalDriver.get() != null;
    }
}
