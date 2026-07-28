package com.ecommerce.automation.constants;

public class AppConstants {
    private AppConstants() {
        throw new UnsupportedOperationException(
                "AppConstants is a utility class and cannot be instantiated!"
        );
    }

    public static final String HOME_PAGE_PATH = "/";
    public static final String LOGIN_PAGE_PATH = "/customer/login";
    public static final String REGISTER_PAGE_PATH = "/customer/register";
    public static final String CART_PAGE_PATH = "/checkout/cart";
    public static final String CHECKOUT_PAGE_PATH = "/checkout";
    public static final String ACCOUNT_PAGE_PATH = "/customer/account";
    public static final String WISHLIST_PAGE_PATH = "/customer/account/wishlist";
    public static final String CONTACT_PAGE_PATH = "/contact";
    public static final String SEARCH_PAGE_PATH = "/search";

    public static final String HOME_PAGE_TITLE = "Demo store - Bagisto";
    public static final String LOGIN_PAGE_TITLE = "Customer Login";
    public static final String REGISTER_PAGE_TITLE = "Customer Signup";
    public static final String CART_PAGE_TITLE = "Shopping Cart";

    public static final int DEFAULT_TIMEOUT = 15;
    public static final int SHORT_TIMEOUT   = 5;
    public static final int LONG_TIMEOUT    = 30;
    public static final int POLL_INTERVAL   = 500;

    public static final String TEST_DATA_FILE_PATH =
            "src/test/resources/testdata/BagistoTestData.xlsx";

    public static final String SHEET_LOGIN      = "Login";
    public static final String SHEET_REGISTER   = "Register";
    public static final String SHEET_SEARCH     = "Search";
    public static final String SHEET_CHECKOUT   = "Checkout";

    public static final String SCREENSHOT_DIR = "target/screenshots/";
    public static final String SCREENSHOT_FORMAT = "PNG";

    public static final String LOGIN_SUCCESS_MSG    = "Welcome";
    public static final String LOGOUT_SUCCESS_MSG   = "You have successfully logged out";
    public static final String REGISTER_SUCCESS_MSG = "Thank you for registering";
    public static final String CART_ADDED_MSG       = "Item Added to Cart Successfully";
    public static final String WISHLIST_ADDED_MSG   = "Item Added to Wishlist Successfully";
    public static final String INVALID_LOGIN_MSG    = "Warning: No match for E-Mail Address";
    public static final String REQUIRED_FIELD_MSG   = "This is a required field";

    public static final String CHROME  = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String EDGE    = "edge";
}
