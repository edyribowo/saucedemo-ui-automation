package com.saucedemo.steps;

import com.saucedemo.hooks.Hooks;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.NavigationMenuPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthSteps {

    private final LoginPage loginPage;
    private final InventoryPage inventoryPage;
    private final NavigationMenuPage navigationMenuPage;

    public AuthSteps() {
        loginPage = new LoginPage(Hooks.getPage());
        inventoryPage = new InventoryPage(Hooks.getPage());
        navigationMenuPage = new NavigationMenuPage(Hooks.getPage());
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage.navigateToLoginPage();
    }

    @Given("I am logged in as {string} with password {string}")
    public void iAmLoggedInAs(String username, String password) {
        loginPage.navigateToLoginPage();
        loginPage.login(username, password);
    }

    @Given("I am not logged in")
    public void iAmNotLoggedIn() {
        loginPage.navigateToLoginPage();
    }

    @When("I enter username {string} and password {string}")
    public void iEnterUsernameAndPassword(String username, String password) {
        if (!username.isEmpty()) loginPage.enterUsername(username);
        if (!password.isEmpty()) loginPage.enterPassword(password);
    }

    @And("I click the Login button")
    public void iClickTheLoginButton() {
        loginPage.clickLoginButton();
    }

    @When("I navigate directly to {string}")
    public void iNavigateDirectlyTo(String path) {
        loginPage.navigateTo(path);
    }

    @Then("I should be redirected to the inventory page")
    public void iShouldBeRedirectedToInventoryPage() {
        assertThat(inventoryPage.isOnInventoryPage())
                .as("Expected to be on inventory page")
                .isTrue();
    }

    @Then("the product list should be displayed")
    public void theProductListShouldBeDisplayed() {
        assertThat(inventoryPage.getProductCount()).isGreaterThan(0);
    }

    @Then("I should see the error message {string}")
    public void iShouldSeeErrorMessage(String expectedMessage) {
        assertThat(loginPage.isErrorDisplayed())
                .as("Error message should be visible")
                .isTrue();
        assertThat(loginPage.getErrorMessage()).contains(expectedMessage);
    }

    @Then("I should remain on the login page")
    public void iShouldRemainOnLoginPage() {
        assertThat(loginPage.isOnLoginPage())
                .as("Expected to remain on login page")
                .isTrue();
    }

    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToLoginPage() {
        assertThat(loginPage.isOnLoginPage())
                .as("Expected to be redirected to login page")
                .isTrue();
    }

    @And("I click Logout")
    public void iClickLogout() {
        navigationMenuPage.clickLogout();
    }
}
