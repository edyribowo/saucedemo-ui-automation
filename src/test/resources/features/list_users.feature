@list-users
Feature: List Users - GET /api/users
  As a consumer of the ReqRes API
  I want to list users via GET /api/users
  So that I can verify pagination behaviour, user object schema, and authentication enforcement

  Background:
    Given the ReqRes API base URL is configured

  @positive @TC-LU-001
  Scenario: TC-LU-001 - List users with default parameters (page 1)
    Given I have a valid API key
    When I send a GET request to "/users"
    Then the response status code should be 200
    And the response body field "page" should equal 1
    And the response body field "per_page" should equal 6
    And the response body "data" array should not be empty
    And the response body field "total" should be a positive integer
    And the response body field "total_pages" should be a positive integer
    And the response body should contain a "support" object

  @positive @TC-LU-002
  Scenario: TC-LU-002 - List users on page 2
    Given I have a valid API key
    When I send a GET request to "/users?page=2"
    Then the response status code should be 200
    And the response body field "page" should equal 2
    And the response body "data" array should not be empty

  @positive @TC-LU-003
  Scenario: TC-LU-003 - List users with custom per_page
    Given I have a valid API key
    When I send a GET request to "/users?per_page=3"
    Then the response status code should be 200
    And the response body field "per_page" should equal 3
    And the response body "data" array should contain exactly 3 item(s)

  @positive @TC-LU-004
  Scenario: TC-LU-004 - Verify all pagination metadata fields
    Given I have a valid API key
    When I send a GET request to "/users?page=1&per_page=6"
    Then the response status code should be 200
    And the response body field "page" should be an integer
    And the response body field "per_page" should be an integer
    And the response body field "total" should be a positive integer
    And the response body field "total_pages" should be a positive integer
    And "total_pages" should equal the ceiling of "total" divided by "per_page"

  @positive @TC-LU-005
  Scenario: TC-LU-005 - Verify user object schema in data array
    Given I have a valid API key
    When I send a GET request to "/users?page=1"
    Then the response status code should be 200
    And every user in the "data" array should have an integer field "id"
    And every user in the "data" array should have a valid email field "email"
    And every user in the "data" array should have a non-empty string field "first_name"
    And every user in the "data" array should have a non-empty string field "last_name"
    And every user in the "data" array should have an HTTPS URL field "avatar"

  @negative @TC-LU-006
  Scenario: TC-LU-006 - List users without API key header
    Given I do not include an API key
    When I send a GET request to "/users?page=1"
    Then the response status code should be 401
    And the response body field "error" should equal "Missing API key."

  @negative @TC-LU-007
  Scenario: TC-LU-007 - List users with invalid API key
    Given I have an invalid API key "totally-wrong-key"
    When I send a GET request to "/users?page=1"
    Then the response status code should be 401
    And the response body should contain an API key error message

  @edge-case @TC-LU-008
  Scenario: TC-LU-008 - List users with page = 0 (below minimum boundary)
    Given I have a valid API key
    When I send a GET request to "/users?page=0"
    Then the response status code should be 200 or 400
    And the response status code should not be a 5xx server error

  @edge-case @TC-LU-009
  Scenario: TC-LU-009 - List users with page beyond total_pages (out-of-range)
    Given I have a valid API key
    When I send a GET request to "/users?page=9999"
    Then the response status code should be 200
    And the response body "data" array should be empty
    And the response body field "total" should be a positive integer
    And the response body field "total_pages" should be a positive integer

  @edge-case @TC-LU-010
  Scenario: TC-LU-010 - List users with negative page number
    Given I have a valid API key
    When I send a GET request to "/users?page=-1"
    Then the response status code should be 400 or 200
    And the response status code should not be a 5xx server error

  @edge-case @TC-LU-011
  Scenario: TC-LU-011 - List users with per_page = 1 (minimum single result)
    Given I have a valid API key
    When I send a GET request to "/users?per_page=1"
    Then the response status code should be 200
    And the response body "data" array should contain exactly 1 item(s)
    And the response body field "per_page" should equal 1
    And "total_pages" should equal the value of "total"

  @edge-case @TC-LU-012
  Scenario: TC-LU-012 - List users with per_page greater than total users
    Given I have a valid API key
    When I send a GET request to "/users?per_page=100"
    Then the response status code should be 200
    And the response body "data" array size should be at most "total" users
    And the response body field "total_pages" should equal 1
    And the response status code should not be a 5xx server error

  @positive @TC-LU-013
  Scenario: TC-LU-013 - Verify avatar URLs are reachable HTTPS links
    Given I have a valid API key
    When I send a GET request to "/users?page=1"
    Then the response status code should be 200
    And every user in the "data" array should have an HTTPS URL field "avatar"

  @positive @TC-LU-014
  Scenario: TC-LU-014 - Verify support object is present and well-formed
    Given I have a valid API key
    When I send a GET request to "/users?page=1"
    Then the response status code should be 200
    And the response body "support" object should have a non-empty "url" field
    And the response body "support" object should have a non-empty "text" field

  @positive @TC-LU-015
  Scenario: TC-LU-015 - Verify page 1 and page 2 return non-overlapping users
    Given I have a valid API key
    When I send a GET request to "/users?page=1"
    And I store the user IDs from the response
    And I send a GET request to "/users?page=2"
    Then the response status code should be 200
    And the user IDs from page 2 should not overlap with user IDs from page 1

  @edge-case @TC-LU-016
  Scenario: TC-LU-016 - List users with non-numeric page parameter
    Given I have a valid API key
    When I send a GET request to "/users?page=abc"
    Then the response status code should be 400 or 200
    And the response status code should not be a 5xx server error
