@inventory
Feature: Product Inventory
  As a logged-in user
  I want to browse and sort the product catalog
  So that I can find items I want to purchase

  # Note: credentials are public demo accounts for https://www.saucedemo.com/ — not real/sensitive data.
  Background:
    Given I am logged in as "standard_user" with password "secret_sauce"
    And I am on the inventory page

  @smoke @positive
  Scenario: TC-INV-001 Verify product list loads after login
    Then 6 products should be displayed
    And each product should have an image, name, description, price, and Add to cart button

  @positive
  Scenario: TC-INV-002 Sort products by price low to high
    When I sort products by "Price (low to high)"
    Then the first product should have price "$7.99"

  @positive
  Scenario: TC-INV-003 Sort products by price high to low
    When I sort products by "Price (high to low)"
    Then the first product should have price "$49.99"

  @positive
  Scenario: TC-INV-004 Sort products by name Z to A
    When I sort products by "Name (Z to A)"
    Then the products should be in reverse alphabetical order

  @positive
  Scenario: TC-INV-005 Navigate to product detail page
    When I click on the product named "Sauce Labs Backpack"
    Then I should be on the product detail page
    And the product detail should show full description, price, and Add to cart button

  @positive
  Scenario: TC-INV-006 Navigate back from product detail to inventory
    When I click on the product named "Sauce Labs Backpack"
    And I click the Back to products button
    Then I should be on the inventory page
