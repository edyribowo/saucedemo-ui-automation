package com.reqres.stepdefs;

import com.reqres.client.BaseApiClient;
import com.reqres.context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps {

    private final ScenarioContext context;
    private final BaseApiClient apiClient;

    public CommonSteps(ScenarioContext context) {
        this.context = context;
        this.apiClient = new BaseApiClient();
    }

    @Given("the ReqRes API base URL is configured")
    public void theReqResApiBaseUrlIsConfigured() {
        // Base URI is set by BaseApiClient; this step exists for feature-file readability
    }

    @Given("I have a valid API key")
    public void iHaveAValidApiKey() {
        context.setRequestSpec(apiClient.withValidApiKey());
    }

    @Given("I do not include an API key")
    public void iDoNotIncludeAnApiKey() {
        context.setRequestSpec(apiClient.withoutApiKey());
    }

    @Given("I have an invalid API key {string}")
    public void iHaveAnInvalidApiKey(String apiKey) {
        context.setRequestSpec(apiClient.withApiKey(apiKey));
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatus) {
        assertThat(context.getResponse().getStatusCode())
                .as("Response status code")
                .isEqualTo(expectedStatus);
    }

    @Then("the response status code should be {int} or {int}")
    public void theResponseStatusCodeShouldBeEither(int status1, int status2) {
        int actual = context.getResponse().getStatusCode();
        assertThat(actual)
                .as("Response status code should be %d or %d", status1, status2)
                .isIn(status1, status2);
    }

    @Then("the response status code should be {int} or a 4xx client error")
    public void theResponseStatusCodeShouldBeOrA4xxClientError(int primaryStatus) {
        int actual = context.getResponse().getStatusCode();
        assertThat(actual)
                .as("Response status code should be %d or any 4xx", primaryStatus)
                .satisfiesAnyOf(
                        code -> assertThat(code).isEqualTo(primaryStatus),
                        code -> assertThat(code).isBetween(400, 499)
                );
    }

    @Then("the response status code should not be a 5xx server error")
    public void theResponseStatusCodeShouldNotBeA5xx() {
        assertThat(context.getResponse().getStatusCode())
                .as("Response status code must not be 5xx")
                .isLessThan(500);
    }

    @Then("the response body field {string} should equal {string}")
    public void theResponseBodyFieldShouldEqualString(String field, String expectedValue) {
        String actualValue = context.getResponse().jsonPath().getString(field);
        assertThat(actualValue)
                .as("Response body field '%s'", field)
                .isEqualTo(expectedValue);
    }

    @Then("the response body field {string} should equal {int}")
    public void theResponseBodyFieldShouldEqualInt(String field, int expectedValue) {
        int actualValue = context.getResponse().jsonPath().getInt(field);
        assertThat(actualValue)
                .as("Response body field '%s'", field)
                .isEqualTo(expectedValue);
    }

    @Then("the response body should have a non-empty string field {string}")
    public void theResponseBodyShouldHaveNonEmptyStringField(String field) {
        String value = context.getResponse().jsonPath().getString(field);
        assertThat(value)
                .as("Response body field '%s' should be a non-empty string", field)
                .isNotNull()
                .isNotEmpty();
    }

    @Then("the response body should not contain field {string}")
    public void theResponseBodyShouldNotContainField(String field) {
        Map<String, Object> body = context.getResponse().jsonPath().getMap("");
        assertThat(body)
                .as("Response body should not contain field '%s'", field)
                .doesNotContainKey(field);
    }

    @Then("the response body should contain an API key error message")
    public void theResponseBodyShouldContainAnApiKeyErrorMessage() {
        String error = context.getResponse().jsonPath().getString("error");
        assertThat(error)
                .as("Response body should contain an API key error message in 'error' field")
                .isNotNull()
                .isNotEmpty();
    }

    @Then("the response body field {string} should be a positive integer")
    public void theResponseBodyFieldShouldBeAPositiveInteger(String field) {
        int value = context.getResponse().jsonPath().getInt(field);
        assertThat(value)
                .as("Response body field '%s' should be a positive integer", field)
                .isGreaterThan(0);
    }

    @Then("the response body field {string} should be an integer")
    public void theResponseBodyFieldShouldBeAnInteger(String field) {
        Object value = context.getResponse().jsonPath().get(field);
        assertThat(value)
                .as("Response body field '%s' should be an integer", field)
                .isInstanceOf(Integer.class);
    }

    @Then("the response body should contain a {string} object")
    public void theResponseBodyShouldContainObject(String field) {
        Map<String, Object> obj = context.getResponse().jsonPath().getMap(field);
        assertThat(obj)
                .as("Response body should contain a non-empty '%s' object", field)
                .isNotNull()
                .isNotEmpty();
    }
}
