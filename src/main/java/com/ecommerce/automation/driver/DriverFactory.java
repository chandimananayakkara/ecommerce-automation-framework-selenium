package com.ecommerce.automation.driver;

import com.ecommerce.automation.constants.AppConstants;
import org.slf4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.LoggerFactory;

public class DriverFactory {
    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);

    private DriverFactory() {
    }

    public static WebDriver createDriver(String browser) {

        log.info("🚀 Creating WebDriver for browser : ", browser.toUpperCase());

        switch (browser.toLowerCase().trim()) {

            case AppConstants.CHROME: {
                log.info("🌐 Launching Chrome browser...");
                return new ChromeDriver(BrowserOptions.getChromeOptions());
            }

            case AppConstants.FIREFOX: {
                log.info("🦊 Launching Firefox browser...");
                return new FirefoxDriver(BrowserOptions.getFirefoxOptions());
            }

            case AppConstants.EDGE: {
                log.info("🔷 Launching Microsoft Edge browser...");
                return new EdgeDriver(BrowserOptions.getEdgeOptions());
            }

            default: {
                log.error("❌ Unsupported browser : Valid options: chrome, firefox, edge",
                        browser);
                throw new IllegalArgumentException(
                        String.format(
                                "Browser is not supported! " +
                                        "Valid values in config.properties: chrome, firefox, edge",
                                browser
                        )
                );
            }
        }
    }
}
