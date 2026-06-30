@checkout
Feature: Checkout
  As a logged-in user with items in my cart
  I want to complete the checkout process
  So that I can place my order

  # Note: credentials are public demo accounts for https://www.saucedemo.com/ — not real/sensitive data.
  Background:
    Given I am logged in as "standard_user" with password "secret_sauce"
    And I have added "Sauce Labs Backpack" to the cart
    And I am on the cart page

  @smoke @positive
  Scenario: TC-CHK-001 Complete checkout with valid information
    When I click the Checkout button
    And I enter checkout information with first name "John" last name "Doe" and zip "12345"
    And I click Continue
    And I click Finish
    Then I should see the order confirmation "Thank you for your order!"

  @negative
  Scenario: TC-CHK-002 Checkout with empty First Name
    When I click the Checkout button
    And I enter checkout information with first name "" last name "Doe" and zip "12345"
    And I click Continue
    Then I should see the checkout error "Error: First Name is required"

  @negative
  Scenario: TC-CHK-003 Checkout with empty Last Name
    When I click the Checkout button
    And I enter checkout information with first name "John" last name "" and zip "12345"
    And I click Continue
    Then I should see the checkout error "Error: Last Name is required"

  @negative
  Scenario: TC-CHK-004 Checkout with empty Zip Code
    When I click the Checkout button
    And I enter checkout information with first name "John" last name "Doe" and zip ""
    And I click Continue
    Then I should see the checkout error "Error: Postal Code is required"

  @positive
  Scenario: TC-CHK-005 Verify price totals on Checkout Overview
    When I click the Checkout button
    And I enter checkout information with first name "John" last name "Doe" and zip "12345"
    And I click Continue
    Then the total price should equal the sum of item total and tax

  @positive
  Scenario: TC-CHK-006 Cancel checkout and return to cart
    When I click the Checkout button
    And I click Cancel on the checkout information page
    Then I should be on the cart page
    And the cart badge should show "1"

  @positive
  Scenario: TC-CHK-007 Cancel on Checkout Overview and return to inventory
    When I click the Checkout button
    And I enter checkout information with first name "John" last name "Doe" and zip "12345"
    And I click Continue
    And I click Cancel on the checkout overview page
    Then I should be on the inventory page
    And the cart badge should show "1"

  @positive
  Scenario: TC-CHK-008 Cart is empty after successful order completion
    When I click the Checkout button
    And I enter checkout information with first name "John" last name "Doe" and zip "12345"
    And I click Continue
    And I click Finish
    And I click Back Home
    Then I should be on the inventory page
    And the cart badge should not be visible
