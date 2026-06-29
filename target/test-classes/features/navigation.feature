@navigation
Feature: Navigation Menu
  As a logged-in user
  I want to use the navigation menu
  So that I can access key application functions

  Background:
    Given I am logged in as "standard_user" with password "secret_sauce"
    And I am on the inventory page

  @smoke @positive
  Scenario: TC-NAV-001 Open and close the burger menu
    When I open the navigation menu
    Then the menu should display All Items, About, Logout, and Reset App State
    When I close the navigation menu
    Then the navigation menu should be closed

  @positive
  Scenario: TC-NAV-002 Reset App State clears the cart
    Given I have added "Sauce Labs Backpack" to the cart
    And I have added "Sauce Labs Bike Light" to the cart
    When I open the navigation menu
    And I click Reset App State
    Then the cart badge should not be visible
    And all Add to cart buttons should be reset
