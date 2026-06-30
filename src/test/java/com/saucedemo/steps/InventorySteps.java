package com.saucedemo.steps;

import com.saucedemo.hooks.Hooks;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.ProductDetailPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InventorySteps {

    private final InventoryPage inventoryPage;
    private final ProductDetailPage productDetailPage;

    public InventorySteps() {
        inventoryPage = new InventoryPage(Hooks.getPage());
        productDetailPage = new ProductDetailPage(Hooks.getPage());
    }

    @Given("I am on the inventory page")
    public void iAmOnInventoryPage() {
        assertThat(inventoryPage.isOnInventoryPage())
                .as("Expected to be on inventory page")
                .isTrue();
    }

    @Then("{int} products should be displayed")
    public void productsCountShouldBe(int expectedCount) {
        assertThat(inventoryPage.getProductCount())
                .as("Expected %d products to be displayed", expectedCount)
                .isEqualTo(expectedCount);
    }

    @Then("each product should have an image, name, description, price, and Add to cart button")
    public void eachProductShouldHaveRequiredElements() {
        assertThat(inventoryPage.allProductsHaveRequiredElements())
                .as("All products should have image, name, description, price, and Add to cart button")
                .isTrue();
    }

    @When("I sort products by {string}")
    public void iSortProductsBy(String sortOption) {
        inventoryPage.sortBy(sortOption);
    }

    @Then("the first product should have price {string}")
    public void theFirstProductShouldHavePrice(String expectedPrice) {
        assertThat(inventoryPage.getFirstProductPrice())
                .as("Expected first product price to be %s", expectedPrice)
                .isEqualTo(expectedPrice);
    }

    @Then("the products should be in reverse alphabetical order")
    public void productsShouldBeInReverseAlphabeticalOrder() {
        List<String> names = inventoryPage.getAllProductNames();
        List<String> sorted = names.stream().sorted(java.util.Comparator.reverseOrder()).toList();
        assertThat(names).as("Products should be sorted Z to A").isEqualTo(sorted);
    }

    @When("I click on the product named {string}")
    public void iClickOnProductNamed(String productName) {
        inventoryPage.clickProductByName(productName);
    }

    @Then("I should be on the product detail page")
    public void iShouldBeOnProductDetailPage() {
        assertThat(productDetailPage.isOnDetailPage())
                .as("Expected to be on product detail page")
                .isTrue();
    }

    @Then("the product detail should show full description, price, and Add to cart button")
    public void productDetailShouldHaveRequiredElements() {
        assertThat(productDetailPage.hasRequiredElements())
                .as("Product detail page should show description, price, and Add to cart button")
                .isTrue();
    }

    @And("I click the Back to products button")
    public void iClickBackToProductsButton() {
        productDetailPage.clickBackToProducts();
    }
}
