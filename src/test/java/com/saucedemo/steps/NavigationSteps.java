package com.saucedemo.steps;

import com.saucedemo.hooks.Hooks;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.NavigationMenuPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class NavigationSteps {

    private final NavigationMenuPage navigationMenuPage;
    private final InventoryPage inventoryPage;

    public NavigationSteps() {
        navigationMenuPage = new NavigationMenuPage(Hooks.getPage());
        inventoryPage = new InventoryPage(Hooks.getPage());
    }

    @When("I open the navigation menu")
    public void iOpenNavigationMenu() {
        navigationMenuPage.openMenu();
    }

    @When("I close the navigation menu")
    public void iCloseNavigationMenu() {
        navigationMenuPage.closeMenu();
    }

    @Then("the menu should display All Items, About, Logout, and Reset App State")
    public void menuShouldDisplayAllItems() {
        assertThat(navigationMenuPage.allMenuItemsVisible())
                .as("Navigation menu should display All Items, About, Logout, and Reset App State")
                .isTrue();
    }

    @Then("the navigation menu should be closed")
    public void navigationMenuShouldBeClosed() {
        assertThat(navigationMenuPage.isMenuClosed())
                .as("Navigation menu should be closed")
                .isTrue();
    }

    @And("I click Reset App State")
    public void iClickResetAppState() {
        navigationMenuPage.clickResetAppState();
    }

    @Then("all Add to cart buttons should be reset")
    public void allAddToCartButtonsShouldBeReset() {
        assertThat(inventoryPage.allAddToCartButtonsReset())
                .as("All buttons should be 'Add to cart' after reset")
                .isTrue();
    }
}
