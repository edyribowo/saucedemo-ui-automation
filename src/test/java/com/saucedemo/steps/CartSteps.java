package com.saucedemo.steps;

import com.saucedemo.hooks.Hooks;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class CartSteps {

    private final InventoryPage inventoryPage;
    private final CartPage cartPage;

    public CartSteps() {
        inventoryPage = new InventoryPage(Hooks.getPage());
        cartPage = new CartPage(Hooks.getPage());
    }

    @Given("I have added {string} to the cart")
    public void iHaveAddedProductToCart(String productName) {
        inventoryPage.addToCart(productName);
    }

    @When("I add {string} to the cart")
    public void iAddProductToCart(String productName) {
        inventoryPage.addToCart(productName);
    }

    @Then("the cart badge should show {string}")
    public void theCartBadgeShouldShow(String expectedCount) {
        assertThat(inventoryPage.isCartBadgeVisible())
                .as("Cart badge should be visible")
                .isTrue();
        assertThat(inventoryPage.getCartBadgeCount())
                .as("Cart badge count should be %s", expectedCount)
                .isEqualTo(expectedCount);
    }

    @Then("the cart badge should not be visible")
    public void theCartBadgeShouldNotBeVisible() {
        assertThat(inventoryPage.isCartBadgeVisible())
                .as("Cart badge should not be visible")
                .isFalse();
    }

    @Then("the {string} button should change to {string}")
    public void theButtonShouldChangeTo(String productName, String expectedLabel) {
        assertThat(inventoryPage.getButtonTextForProduct(productName))
                .as("Button text for %s should be %s", productName, expectedLabel)
                .isEqualToIgnoringCase(expectedLabel);
    }

    @When("I click the cart icon")
    public void iClickTheCartIcon() {
        inventoryPage.clickCartIcon();
    }

    @Then("the cart page should show {int} items")
    public void theCartPageShouldShowItems(int expectedCount) {
        assertThat(cartPage.getCartItemCount())
                .as("Cart should contain %d items", expectedCount)
                .isEqualTo(expectedCount);
    }

    @When("I click Remove for {string} on the inventory page")
    public void iClickRemoveOnInventoryPage(String productName) {
        inventoryPage.removeFromInventory(productName);
    }

    @When("I remove {string} from the cart page")
    public void iRemoveFromCartPage(String productName) {
        cartPage.removeItemByName(productName);
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        assertThat(cartPage.isCartEmpty())
                .as("Cart should be empty")
                .isTrue();
    }

    @And("I click Continue Shopping")
    public void iClickContinueShopping() {
        cartPage.clickContinueShopping();
    }

    @Then("the Checkout button should be visible")
    public void theCheckoutButtonShouldBeVisible() {
        assertThat(cartPage.isCheckoutButtonVisible())
                .as("Checkout button should be visible")
                .isTrue();
    }

    @Given("I am on the cart page")
    public void iAmOnCartPage() {
        inventoryPage.clickCartIcon();
        assertThat(cartPage.isOnCartPage())
                .as("Expected to be on cart page")
                .isTrue();
    }
}
