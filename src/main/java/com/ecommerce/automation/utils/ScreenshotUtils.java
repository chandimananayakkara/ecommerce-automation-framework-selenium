package com.ecommerce.automation.utils;

import com.ecommerce.automation.constants.AppConstants;
import org.slf4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {
    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private ScreenshotUtils() {}

    public static String captureScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            log.warn("⚠️ Cannot capture screenshot — driver is null");
            return null;
        }

        try {
           TakesScreenshot screenshotDriver = (TakesScreenshot) driver;

            File screenshotFile = screenshotDriver.getScreenshotAs(OutputType.FILE);

            Path screenshotDir = Paths.get(AppConstants.SCREENSHOT_DIR);
            Files.createDirectories(screenshotDir);  String timestamp = LocalDateTime.now().format(DATE_FORMAT);
            String fileName = testName + "_" + timestamp + ".png";
            Path destinationPath = screenshotDir.resolve(fileName);

            Files.copy(screenshotFile.toPath(), destinationPath,
                    StandardCopyOption.REPLACE_EXISTING);

            log.info("📸 Screenshot saved: {}", destinationPath.toAbsolutePath());
            return destinationPath.toAbsolutePath().toString();

        } catch (IOException e) {
            log.error("❌ Failed to save screenshot for test : ",
                    testName, e.getMessage());
            return null;
        } catch (ClassCastException e) {
            log.error("❌ WebDriver does not support screenshots : ");
            return null;
        }
    }

    public static byte[] captureScreenshotAsBytes(WebDriver driver) {
        if (driver == null) {
            log.warn("⚠️ Cannot capture screenshot as bytes — driver is null");
            return new byte[0];
        }
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            log.error("❌ Failed to capture screenshot as bytes : ", e.getMessage());
            return new byte[0];
        }
    }
}
