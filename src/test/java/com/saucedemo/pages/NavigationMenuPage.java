package com.saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.saucedemo.base.BasePage;

public class NavigationMenuPage extends BasePage {

    private final Locator burgerMenuButton;
    private final Locator closeMenuButton;
    private final Locator allItemsLink;
    private final Locator aboutLink;
    private final Locator logoutLink;
    private final Locator resetAppStateLink;

    public NavigationMenuPage(Page page) {
        super(page);
        burgerMenuButton = page.locator("#react-burger-menu-btn");
        closeMenuButton = page.locator("#react-burger-cross-btn");
        allItemsLink = page.locator("#inventory_sidebar_link");
        aboutLink = page.locator("#about_sidebar_link");
        logoutLink = page.locator("#logout_sidebar_link");
        resetAppStateLink = page.locator("#reset_sidebar_link");
    }

    public void openMenu() {
        click(burgerMenuButton);
    }

    public void closeMenu() {
        click(closeMenuButton);
    }

    public boolean isMenuClosed() {
        return isHidden(allItemsLink);
    }

    public boolean allMenuItemsVisible() {
        return isVisible(allItemsLink)
                && isVisible(aboutLink)
                && isVisible(logoutLink)
                && isVisible(resetAppStateLink);
    }

    public void clickLogout() {
        click(logoutLink);
    }

    public void clickResetAppState() {
        click(resetAppStateLink);
    }
}
