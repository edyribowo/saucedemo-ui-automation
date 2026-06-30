package com.saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.saucedemo.base.BasePage;

import java.util.List;

public class InventoryPage extends BasePage {

    private static final String INVENTORY_URL = "https://www.saucedemo.com/inventory.html";

    private final Locator inventoryItems;
    private final Locator sortDropdown;
    private final Locator cartBadge;
    private final Locator cartIcon;

    public InventoryPage(Page page) {
        super(page);
        inventoryItems = page.locator("[data-test='inventory-item']");
        sortDropdown = page.locator("[data-test='product-sort-container']");
        cartBadge = page.locator("[data-test='shopping-cart-badge']");
        cartIcon = page.locator("[data-test='shopping-cart-link']");
    }

    public boolean isOnInventoryPage() {
        return page.url().contains("/inventory.html");
    }

    public int getProductCount() {
        inventoryItems.first().waitFor();
        return inventoryItems.count();
    }

    public boolean allProductsHaveRequiredElements() {
        int count = getProductCount();
        for (int i = 0; i < count; i++) {
            Locator item = inventoryItems.nth(i);
            if (item.locator("img").count() == 0) return false;
            if (item.locator("[data-test='inventory-item-name']").count() == 0) return false;
            if (item.locator("[data-test='inventory-item-desc']").count() == 0) return false;
            if (item.locator("[data-test='inventory-item-price']").count() == 0) return false;
            if (item.locator("button").count() == 0) return false;
        }
        return true;
    }

    public void sortBy(String option) {
        selectDropdownByLabel(sortDropdown, option);
    }

    public String getFirstProductPrice() {
        return getText(inventoryItems.first().locator("[data-test='inventory-item-price']"));
    }

    public List<String> getAllProductNames() {
        return page.locator("[data-test='inventory-item-name']").allInnerTexts();
    }

    public void clickProductByName(String productName) {
        click(page.locator("[data-test='inventory-item-name']", new Page.LocatorOptions().setHasText(productName)));
    }

    public void addToCart(String productName) {
        Locator item = getItemByName(productName);
        click(item.locator("button"));
    }

    public void removeFromInventory(String productName) {
        Locator item = getItemByName(productName);
        click(item.locator("button"));
    }

    public String getButtonTextForProduct(String productName) {
        return getText(getItemByName(productName).locator("button"));
    }

    public boolean isCartBadgeVisible() {
        return isVisible(cartBadge);
    }

    public String getCartBadgeCount() {
        return getText(cartBadge);
    }

    public void clickCartIcon() {
        click(cartIcon);
    }

    public boolean allAddToCartButtonsReset() {
        List<String> buttonTexts = page.locator("[data-test='inventory-item'] button").allInnerTexts();
        return buttonTexts.stream().allMatch(t -> t.equalsIgnoreCase("Add to cart"));
    }

    private Locator getItemByName(String productName) {
        return page.locator("[data-test='inventory-item']")
                .filter(new Locator.FilterOptions().setHasText(productName));
    }
}
