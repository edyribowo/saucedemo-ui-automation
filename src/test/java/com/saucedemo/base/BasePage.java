package com.saucedemo.base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public abstract class BasePage {

    protected static final String BASE_URL = System.getProperty("base.url", "https://www.saucedemo.com/");

    protected final Page page;
    private static final int DEFAULT_TIMEOUT_MS = 10_000;

    protected BasePage(Page page) {
        this.page = page;
    }

    protected void click(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_TIMEOUT_MS));
        locator.click();
    }

    protected void type(Locator locator, String text) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_TIMEOUT_MS));
        locator.clear();
        locator.fill(text);
    }

    protected String getText(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_TIMEOUT_MS));
        return locator.innerText().trim();
    }

    protected boolean isVisible(Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(3_000));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isHidden(Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.HIDDEN)
                    .setTimeout(3_000));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void selectDropdownByLabel(Locator locator, String label) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_TIMEOUT_MS));
        locator.selectOption(new com.microsoft.playwright.options.SelectOption().setLabel(label));
    }

    protected void navigateTo(String url) {
        page.navigate(url);
    }
}
