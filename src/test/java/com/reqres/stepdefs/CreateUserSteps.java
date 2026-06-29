package com.reqres.stepdefs;

import com.reqres.context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserSteps {

    private final ScenarioContext context;

    public CreateUserSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("I send a POST request to {string} with body:")
    public void iSendAPostRequestWithBody(String path, String body) {
        Response response = RestAssured
                .given(context.getRequestSpec())
                .body(body)
                .post(path)
                .then()
                .log().all()
                .extract()
                .response();
        context.setResponse(response);
    }

    @When("I send a POST request to {string} with a 1000-character name and 1000-character job")
    public void iSendAPostRequestWithLongStrings(String path) {
        String body = String.format("{\"name\": \"%s\", \"job\": \"%s\"}",
                "a".repeat(1000), "b".repeat(1000));
        Response response = RestAssured
                .given(context.getRequestSpec())
                .body(body)
                .post(path)
                .then()
                .log().all()
                .extract()
                .response();
        context.setResponse(response);
    }

    @Then("the response body should have a valid ISO 8601 timestamp field {string}")
    public void theResponseBodyShouldHaveValidIso8601TimestampField(String field) {
        String value = context.getResponse().jsonPath().getString(field);
        assertThat(value)
                .as("Response body field '%s' should be non-empty", field)
                .isNotNull()
                .isNotEmpty();
        try {
            Instant.parse(value);
        } catch (DateTimeParseException e) {
            throw new AssertionError(
                    String.format("Field '%s' value '%s' is not a valid ISO 8601 timestamp", field, value), e);
        }
    }

    @Then("the {string} timestamp should be within a reasonable tolerance of the current UTC time")
    public void theTimestampShouldBeWithinToleranceOfCurrentUtc(String field) {
        String value = context.getResponse().jsonPath().getString(field);
        Instant responseTime = Instant.parse(value);
        long diffSeconds = Math.abs(Instant.now().getEpochSecond() - responseTime.getEpochSecond());
        assertThat(diffSeconds)
                .as("Field '%s' timestamp should be within 60 seconds of current UTC", field)
                .isLessThanOrEqualTo(60);
    }

    @Then("the response body field {string} should be of type string")
    public void theResponseBodyFieldShouldBeOfTypeString(String field) {
        Object value = context.getResponse().jsonPath().get(field);
        assertThat(value)
                .as("Response body field '%s' should be a String, but was: %s",
                        field, value == null ? "null" : value.getClass().getSimpleName())
                .isInstanceOf(String.class);
    }
}
