package com.ecommerce.automation.driver;

import com.ecommerce.automation.config.ConfigManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BrowserOptions {
    private static final Logger log = (Logger) LoggerFactory.getLogger(BrowserOptions.class);
    private static final ConfigManager config = ConfigManager.getInstance();

    private BrowserOptions() {
    }

    public static ChromeOptions getChromeOptions() {
        log.info("🔧 Building Chrome options... [ Headless : ]", config.isHeadless());

        ChromeOptions options = new ChromeOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless=new");
            log.info("✅ Headless mode ENABLED");
        }

        options.addArguments("--window-size=1920,1080");

        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--disable-extensions",
                "--disable-infobars",
                "--remote-allow-origins=*"

        );

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.default_directory", System.getProperty("user.dir") + "/downloads");

        options.setExperimentalOption("prefs", prefs);

        options.setExperimentalOption("excludeSwitches",
                new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        log.info("✅ Chrome options built successfully");
        return options;
    }

    public static FirefoxOptions getFirefoxOptions() {
        log.info("🔧 Building Firefox options... [Headless: {}]", config.isHeadless());

        FirefoxOptions options = new FirefoxOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }

        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("app.update.enabled", false);
        options.addPreference("dom.disable_beforeunload", true);
        options.addPreference("signon.rememberSignons", false);

        log.info("✅ Firefox options built successfully");
        return options;
    }

    public static EdgeOptions getEdgeOptions() {
        log.info("🔧 Building Edge options... [Headless: {}]", config.isHeadless());

        EdgeOptions options = new EdgeOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--disable-extensions",
                "--remote-allow-origins=*"
        );

        options.addArguments("--no-first-run");
        options.addArguments("--no-default-browser-check");

        log.info("✅ Edge options built successfully");
        return options;
    }
}
