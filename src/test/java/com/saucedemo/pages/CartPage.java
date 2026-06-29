package com.saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.saucedemo.base.BasePage;

public class CartPage extends BasePage {

    private final Locator cartItems;
    private final Locator checkoutButton;
    private final Locator continueShoppingButton;
    private final Locator cartBadge;

    public CartPage(Page page) {
        super(page);
        cartItems = page.locator(".cart_item");
        checkoutButton = page.locator("[data-test='checkout']");
        continueShoppingButton = page.locator("[data-test='continue-shopping']");
        cartBadge = page.locator(".shopping_cart_badge");
    }

    public boolean isOnCartPage() {
        return page.url().contains("/cart.html");
    }

    public int getCartItemCount() {
        return cartItems.count();
    }

    public boolean isCartEmpty() {
        return cartItems.count() == 0;
    }

    public void removeItemByName(String productName) {
        Locator item = page.locator(".cart_item")
                .filter(new Locator.FilterOptions().setHasText(productName));
        click(item.locator("button[data-test^='remove']"));
    }

    public void clickCheckout() {
        click(checkoutButton);
    }

    public void clickContinueShopping() {
        click(continueShoppingButton);
    }

    public boolean isCheckoutButtonVisible() {
        return isVisible(checkoutButton);
    }

    public boolean isCartBadgeVisible() {
        return isVisible(cartBadge);
    }

    public String getCartBadgeCount() {
        return getText(cartBadge);
    }
}
