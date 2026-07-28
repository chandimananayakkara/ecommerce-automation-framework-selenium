package com.ecommerce.automation.tests;

import com.ecommerce.automation.base.BaseTest;
import com.ecommerce.automation.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test(description = "Verify Bagisto homepage loads successfully")
    public void verifyHomepageLoads() {

        String actualTitle = getPageTitle();
        String currentUrl = getCurrentUrl();

        System.out.println("✅ Page Title: " + actualTitle);
        System.out.println("✅ Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("bagisto.com"), "❌ URL does not contain 'bagisto.com'! Actual URL: " + currentUrl);

        Assert.assertFalse(actualTitle.isEmpty(), "❌ Page title should not be empty!");

        System.out.println("🎉 Smoke test PASSED! Framework Stage 2 is working!");
    }

    @Test(description = "Verify navigation to login page works")
    public void verifyLoginPageNavigation() {

        navigateTo(AppConstants.LOGIN_PAGE_PATH);

        String currentUrl = getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("login"), "❌ Should be on login page! Actual URL: " + currentUrl);

        System.out.println("✅ Login page navigation working! URL: " + currentUrl);
    }

    @Test(description = "Verify navigation to register page works")
    public void verifyRegisterPageNavigation() {

        navigateTo(AppConstants.REGISTER_PAGE_PATH);

        String currentUrl = getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("register"), "❌ Should be on register page! Actual URL: " + currentUrl);

        System.out.println("✅ Register page navigation working! URL: " + currentUrl);
    }
}
