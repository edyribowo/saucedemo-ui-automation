package com.saucedemo.steps;

import com.saucedemo.hooks.Hooks;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class CheckoutSteps {

    private final CartPage cartPage;
    private final CheckoutPage checkoutPage;
    private final InventoryPage inventoryPage;

    public CheckoutSteps() {
        cartPage = new CartPage(Hooks.getPage());
        checkoutPage = new CheckoutPage(Hooks.getPage());
        inventoryPage = new InventoryPage(Hooks.getPage());
    }

    @When("I click the Checkout button")
    public void iClickCheckoutButton() {
        cartPage.clickCheckout();
    }

    @And("I enter checkout information with first name {string} last name {string} and zip {string}")
    public void iEnterCheckoutInformation(String firstName, String lastName, String zip) {
        checkoutPage.enterCheckoutInfo(firstName, lastName, zip);
    }

    @And("I click Continue")
    public void iClickContinue() {
        checkoutPage.clickContinue();
    }

    @And("I click Finish")
    public void iClickFinish() {
        checkoutPage.clickFinish();
    }

    @And("I click Back Home")
    public void iClickBackHome() {
        checkoutPage.clickBackHome();
    }

    @And("I click Cancel on the checkout information page")
    public void iClickCancelOnCheckoutInfo() {
        checkoutPage.clickCancel();
    }

    @And("I click Cancel on the checkout overview page")
    public void iClickCancelOnOverview() {
        checkoutPage.clickCancel();
    }

    @Then("I should see the order confirmation {string}")
    public void iShouldSeeOrderConfirmation(String expectedMessage) {
        assertThat(checkoutPage.isOnConfirmationPage())
                .as("Expected to be on confirmation page")
                .isTrue();
        assertThat(checkoutPage.getConfirmationMessage())
                .as("Confirmation message should match")
                .contains(expectedMessage);
    }

    @Then("I should see the checkout error {string}")
    public void iShouldSeeCheckoutError(String expectedError) {
        assertThat(checkoutPage.isErrorDisplayed())
                .as("Checkout error should be visible")
                .isTrue();
        assertThat(checkoutPage.getErrorMessage())
                .as("Checkout error message should match")
                .contains(expectedError);
    }

    @Then("the total price should equal the sum of item total and tax")
    public void totalShouldEqualItemTotalPlusTax() {
        double itemTotal = checkoutPage.getItemTotal();
        double tax = checkoutPage.getTaxAmount();
        double total = checkoutPage.getTotal();
        assertThat(itemTotal + tax)
                .as("Total (%.2f) should equal item total (%.2f) + tax (%.2f)", total, itemTotal, tax)
                .isCloseTo(total, offset(0.01));
    }

    @Then("I should be on the cart page")
    public void iShouldBeOnCartPage() {
        assertThat(cartPage.isOnCartPage())
                .as("Expected to be on cart page")
                .isTrue();
    }

    @Then("I should be on the inventory page")
    public void iShouldBeOnInventoryPage() {
        assertThat(inventoryPage.isOnInventoryPage())
                .as("Expected to be on inventory page")
                .isTrue();
    }
}
