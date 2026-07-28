package com.ecommerce.automation.base;

import com.ecommerce.automation.config.ConfigManager;
import com.ecommerce.automation.driver.DriverManager;
import com.ecommerce.automation.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected final ConfigManager config = ConfigManager.getInstance();

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        log.info("╔══════════════════════════════════════════════╗");
        log.info("║   ECOMMERCE AUTOMATION FRAMEWORK STARTED        ║");
        log.info("║   Environment : ", config.getBaseUrl());
        log.info("║   Browser     : ", config.getBrowser().toUpperCase());
        log.info("║   Headless    : ", config.isHeadless());
        log.info("╚══════════════════════════════════════════════╝");
    }


    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("") String browser) {

        String browserToUse = (browser == null || browser.isEmpty())
                ? config.getBrowser()
                : browser;

        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info("🧪 Setting up test",
                browserToUse.toUpperCase(), Thread.currentThread().getName());

        DriverManager.initDriver(browserToUse);

        String url = config.getBaseUrl();
        DriverManager.getDriver().get(url);
        log.info("🌐 Navigated to : ", url);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            log.error("❌ TEST FAILED : ", result.getName());
            log.error("   Reason : ", result.getThrowable().getMessage());

            if (config.isScreenshotOnFailure()) {
                ScreenshotUtils.captureScreenshot(
                        DriverManager.getDriver(),
                        result.getName()
                );
                log.info("📸 Screenshot captured for failed test : ", result.getName());
            }

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("✅ TEST PASSED : ", result.getName());

        } else if (result.getStatus() == ITestResult.SKIP) {
            log.warn("⏭️ TEST SKIPPED : ", result.getName());
        }

        DriverManager.quitDriver();

        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTearDown() {
        log.info("╔══════════════════════════════════════════════╗");
        log.info("║   ECOMMERCE AUTOMATION SUITE COMPLETED       ║");
        log.info("╚══════════════════════════════════════════════╝");
    }


    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected void navigateTo(String path) {
        String fullUrl = config.getBaseUrl() + path;
        getDriver().get(fullUrl);
        log.info("🔀 Navigated to : ", fullUrl);
    }

    protected String getPageTitle() {
        return getDriver().getTitle();
    }

    protected String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }
}
