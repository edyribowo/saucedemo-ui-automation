@auth
Feature: Authentication
  As a user of Swag Labs
  I want to be able to log in and log out securely
  So that I can access the product inventory

  Background:
    Given I am on the login page

  @smoke @positive
  Scenario: TC-AUTH-001 Successful login with valid credentials
    When I enter username "standard_user" and password "secret_sauce"
    And I click the Login button
    Then I should be redirected to the inventory page
    And the product list should be displayed

  @negative
  Scenario: TC-AUTH-002 Login with invalid password
    When I enter username "standard_user" and password "wrongpassword"
    And I click the Login button
    Then I should see the error message "Epic sadface: Username and password do not match any user in this service"
    And I should remain on the login page

  @negative
  Scenario: TC-AUTH-003 Login with invalid username
    When I enter username "unknown_user" and password "secret_sauce"
    And I click the Login button
    Then I should see the error message "Epic sadface: Username and password do not match any user in this service"

  @negative
  Scenario: TC-AUTH-004 Login with empty username field
    When I enter username "" and password "secret_sauce"
    And I click the Login button
    Then I should see the error message "Epic sadface: Username is required"

  @negative
  Scenario: TC-AUTH-005 Login with empty password field
    When I enter username "standard_user" and password ""
    And I click the Login button
    Then I should see the error message "Epic sadface: Password is required"

  @negative @edge
  Scenario: TC-AUTH-006 Login with both fields empty
    When I click the Login button
    Then I should see the error message "Epic sadface: Username is required"

  @negative
  Scenario: TC-AUTH-007 Login attempt with locked-out user
    When I enter username "locked_out_user" and password "secret_sauce"
    And I click the Login button
    Then I should see the error message "Epic sadface: Sorry, this user has been locked out"

  @positive
  Scenario: TC-AUTH-008 Successful logout
    Given I am logged in as "standard_user" with password "secret_sauce"
    When I open the navigation menu
    And I click Logout
    Then I should be redirected to the login page

  @negative @security
  Scenario: TC-AUTH-009 Access inventory page without authentication
    Given I am not logged in
    When I navigate directly to "/inventory.html"
    Then I should be redirected to the login page
    And I should see the error message "Epic sadface: You can only access '/inventory.html' when you are logged in."
