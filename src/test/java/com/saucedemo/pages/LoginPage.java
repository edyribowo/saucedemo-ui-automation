package com.saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.saucedemo.base.BasePage;

public class LoginPage extends BasePage {

    private static final String BASE_URL = "https://www.saucedemo.com/";

    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator errorMessage;

    public LoginPage(Page page) {
        super(page);
        usernameInput = page.locator("#user-name");
        passwordInput = page.locator("#password");
        loginButton = page.locator("#login-button");
        errorMessage = page.locator("[data-test='error']");
    }

    public void navigateToLoginPage() {
        navigateTo(BASE_URL);
    }

    public void navigateTo(String path) {
        page.navigate(BASE_URL.replaceAll("/$", "") + path);
    }

    public void enterUsername(String username) {
        type(usernameInput, username);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isVisible(errorMessage);
    }

    public boolean isOnLoginPage() {
        return page.url().equals(BASE_URL) || page.url().contains("index.html");
    }
}
