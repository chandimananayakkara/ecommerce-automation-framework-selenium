package com.ecommerce.automation.config;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Logger log = (Logger) LoggerFactory.getLogger(ConfigManager.class);

    private static volatile ConfigManager instance;

    private final Properties properties;

    private static final String CONFIG_FILE_PATH =
            "src/test/resources/config/config.properties";

      private ConfigManager() {
        properties = new Properties();
        loadProperties();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
            log.info("✅ Configuration loaded successfully from :", CONFIG_FILE_PATH);
        } catch (IOException e) {
            log.error("❌ FAILED to load config file : ", CONFIG_FILE_PATH);
            throw new RuntimeException(
                    "Cannot load configuration file. " +
                            "Please ensure config.properties exists at : " + CONFIG_FILE_PATH, e
            );
        }
    }

    public String getProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isEmpty()) {
            log.debug("🔧 Using system property for key : ", key, systemValue);
            return systemValue;
        }

        String value = properties.getProperty(key);
        if (value == null) {
            log.error("❌ Property not found : ", key);
            throw new RuntimeException(
                    "Property '" + key + "' not found in config.properties!"
            );
        }
        return value.trim();
    }

   public String getBaseUrl() {
        return getProperty("base.url");
    }

    public String getBrowser() {
        return getProperty("browser");
    }

    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout"));
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }

    public boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure"));
    }

    public String getValidEmail() {
        return getProperty("valid.email");
    }

    public String getValidPassword() {
        return getProperty("valid.password");
    }
}
