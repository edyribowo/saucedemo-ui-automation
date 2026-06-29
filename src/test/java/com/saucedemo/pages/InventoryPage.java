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
        inventoryItems = page.locator(".inventory_item");
        sortDropdown = page.locator("[data-test='product_sort_container']");
        cartBadge = page.locator(".shopping_cart_badge");
        cartIcon = page.locator(".shopping_cart_link");
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
            if (item.locator(".inventory_item_img img").count() == 0) return false;
            if (item.locator(".inventory_item_name").count() == 0) return false;
            if (item.locator(".inventory_item_desc").count() == 0) return false;
            if (item.locator(".inventory_item_price").count() == 0) return false;
            if (item.locator("button").count() == 0) return false;
        }
        return true;
    }

    public void sortBy(String option) {
        selectDropdownByLabel(sortDropdown, option);
    }

    public String getFirstProductPrice() {
        return getText(inventoryItems.first().locator(".inventory_item_price"));
    }

    public List<String> getAllProductNames() {
        return page.locator(".inventory_item_name").allInnerTexts();
    }

    public void clickProductByName(String productName) {
        click(page.locator(".inventory_item_name", new Page.LocatorOptions().setHasText(productName)));
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
        List<String> buttonTexts = page.locator(".inventory_item button").allInnerTexts();
        return buttonTexts.stream().allMatch(t -> t.equalsIgnoreCase("Add to cart"));
    }

    private Locator getItemByName(String productName) {
        return page.locator(".inventory_item")
                .filter(new Locator.FilterOptions().setHasText(productName));
    }
}
