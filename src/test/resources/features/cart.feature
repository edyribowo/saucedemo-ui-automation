@cart
Feature: Shopping Cart
  As a logged-in user
  I want to manage items in my shopping cart
  So that I can prepare my order before checkout

  Background:
    Given I am logged in as "standard_user" with password "secret_sauce"
    And I am on the inventory page

  @smoke @positive
  Scenario: TC-CART-001 Add a single product to cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge should show "1"
    And the "Sauce Labs Backpack" button should change to "Remove"

  @positive
  Scenario: TC-CART-002 Add multiple products to cart
    When I add "Sauce Labs Backpack" to the cart
    And I add "Sauce Labs Bike Light" to the cart
    And I click the cart icon
    Then the cart page should show 2 items
    And the cart badge should show "2"

  @positive
  Scenario: TC-CART-003 Remove a product from the inventory page
    Given I have added "Sauce Labs Backpack" to the cart
    When I click Remove for "Sauce Labs Backpack" on the inventory page
    Then the cart badge should not be visible
    And the "Sauce Labs Backpack" button should change to "Add to cart"

  @positive
  Scenario: TC-CART-004 Remove a product from the cart page
    Given I have added "Sauce Labs Backpack" to the cart
    When I click the cart icon
    And I remove "Sauce Labs Backpack" from the cart page
    Then the cart should be empty

  @positive
  Scenario: TC-CART-005 Continue shopping from cart page
    Given I have added "Sauce Labs Backpack" to the cart
    When I click the cart icon
    And I click Continue Shopping
    Then I should be on the inventory page
    And the cart badge should show "1"

  @edge
  Scenario: TC-CART-006 View empty cart
    When I click the cart icon
    Then the cart should be empty
    And the Checkout button should be visible
