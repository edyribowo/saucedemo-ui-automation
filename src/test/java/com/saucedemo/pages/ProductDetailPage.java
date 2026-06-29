package com.saucedemo.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.saucedemo.base.BasePage;

public class ProductDetailPage extends BasePage {

    private final Locator productName;
    private final Locator productDescription;
    private final Locator productPrice;
    private final Locator addToCartButton;
    private final Locator backButton;

    public ProductDetailPage(Page page) {
        super(page);
        productName = page.locator(".inventory_details_name");
        productDescription = page.locator(".inventory_details_desc");
        productPrice = page.locator(".inventory_details_price");
        addToCartButton = page.locator("[data-test^='add-to-cart']");
        backButton = page.locator("[data-test='back-to-products']");
    }

    public boolean isOnDetailPage() {
        return page.url().contains("/inventory-item.html");
    }

    public boolean hasRequiredElements() {
        return isVisible(productDescription) && isVisible(productPrice) && isVisible(addToCartButton);
    }

    public void clickBackToProducts() {
        click(backButton);
    }
}
