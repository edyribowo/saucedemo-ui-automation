@create-user
Feature: Create User - POST /api/users
  As a consumer of the ReqRes API
  I want to create users via POST /api/users
  So that I can verify field handling, response schema, and authentication enforcement

  Background:
    Given the ReqRes API base URL is configured

  @positive @TC-CU-001
  Scenario: TC-CU-001 - Create user with valid name and job
    Given I have a valid API key
    When I send a POST request to "/users" with body:
      """
      {"name": "morpheus", "job": "leader"}
      """
    Then the response status code should be 201
    And the response body field "name" should equal "morpheus"
    And the response body field "job" should equal "leader"
    And the response body should have a non-empty string field "id"
    And the response body should have a valid ISO 8601 timestamp field "createdAt"

  @positive @TC-CU-002
  Scenario: TC-CU-002 - Create user with name only (no job field)
    Given I have a valid API key
    When I send a POST request to "/users" with body:
      """
      {"name": "trinity"}
      """
    Then the response status code should be 201
    And the response body field "name" should equal "trinity"
    And the response body should not contain field "job"
    And the response body should have a non-empty string field "id"
    And the response body should have a valid ISO 8601 timestamp field "createdAt"

  @positive @TC-CU-003
  Scenario: TC-CU-003 - Create user with job only (no name field)
    Given I have a valid API key
    When I send a POST request to "/users" with body:
      """
      {"job": "zion resident"}
      """
    Then the response status code should be 201
    And the response body field "job" should equal "zion resident"
    And the response body should not contain field "name"
    And the response body should have a non-empty string field "id"
    And the response body should have a valid ISO 8601 timestamp field "createdAt"

  @edge-case @TC-CU-004
  Scenario: TC-CU-004 - Create user with empty JSON body
    Given I have a valid API key
    When I send a POST request to "/users" with body:
      """
      {}
      """
    Then the response status code should be 201
    And the response body should have a non-empty string field "id"
    And the response body should have a valid ISO 8601 timestamp field "createdAt"
    And the response body should not contain field "name"
    And the response body should not contain field "job"

  @edge-case @TC-CU-005
  Scenario: TC-CU-005 - Create user with additional unexpected fields
    Given I have a valid API key
    When I send a POST request to "/users" with body:
      """
      {"name": "neo", "job": "the one", "age": 30, "active": true}
      """
    Then the response status code should be 201
    And the response body field "name" should equal "neo"
    And the response body field "job" should equal "the one"
    And the response body should have a non-empty string field "id"
    And the response body should have a valid ISO 8601 timestamp field "createdAt"

  @negative @TC-CU-006
  Scenario: TC-CU-006 - Create user without API key header
    Given I do not include an API key
    When I send a POST request to "/users" with body:
      """
      {"name": "morpheus", "job": "leader"}
      """
    Then the response status code should be 401
    And the response body field "error" should equal "Missing API key."

  @negative @TC-CU-007
  Scenario: TC-CU-007 - Create user with invalid API key
    Given I have an invalid API key "invalid-key-xyz"
    When I send a POST request to "/users" with body:
      """
      {"name": "morpheus", "job": "leader"}
      """
    Then the response status code should be 401
    And the response body should contain an API key error message

  @edge-case @TC-CU-008
  Scenario: TC-CU-008 - Create user with special characters in name and job
    Given I have a valid API key
    When I send a POST request to "/users" with body:
      """
      {"name": "Ñoño O'Brien", "job": "Dev & QA <Lead>"}
      """
    Then the response status code should be 201
    And the response body field "name" should equal "Ñoño O'Brien"
    And the response body field "job" should equal "Dev & QA <Lead>"
    And the response body should have a non-empty string field "id"
    And the response body should have a valid ISO 8601 timestamp field "createdAt"

  @edge-case @TC-CU-009
  Scenario: TC-CU-009 - Create user with numeric string values
    Given I have a valid API key
    When I send a POST request to "/users" with body:
      """
      {"name": "12345", "job": "007"}
      """
    Then the response status code should be 201
    And the response body field "name" should equal "12345"
    And the response body field "job" should equal "007"
    And the response body should have a non-empty string field "id"
    And the response body should have a valid ISO 8601 timestamp field "createdAt"

  @edge-case @TC-CU-010
  Scenario: TC-CU-010 - Create user with very long string values (boundary)
    Given I have a valid API key
    When I send a POST request to "/users" with a 1000-character name and 1000-character job
    Then the response status code should be 201 or a 4xx client error
    And the response status code should not be a 5xx server error

  @positive @TC-CU-011
  Scenario: TC-CU-011 - Verify response id field type is string
    Given I have a valid API key
    When I send a POST request to "/users" with body:
      """
      {"name": "morpheus", "job": "leader"}
      """
    Then the response status code should be 201
    And the response body field "id" should be of type string

  @positive @TC-CU-012
  Scenario: TC-CU-012 - Verify createdAt is a valid ISO 8601 timestamp
    Given I have a valid API key
    When I send a POST request to "/users" with body:
      """
      {"name": "morpheus", "job": "leader"}
      """
    Then the response status code should be 201
    And the response body should have a valid ISO 8601 timestamp field "createdAt"
    And the "createdAt" timestamp should be within a reasonable tolerance of the current UTC time

  @edge-case @TC-CU-013
  Scenario: TC-CU-013 - Create user with null field values
    Given I have a valid API key
    When I send a POST request to "/users" with body:
      """
      {"name": null, "job": null}
      """
    Then the response status code should be 201 or a 4xx client error
    And the response status code should not be a 5xx server error
