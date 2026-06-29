package com.reqres.stepdefs;

import com.reqres.context.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ListUsersSteps {

    private final ScenarioContext context;

    public ListUsersSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String path) {
        Response response = RestAssured
                .given(context.getRequestSpec())
                .get(path)
                .then()
                .log().all()
                .extract()
                .response();
        context.setResponse(response);
    }

    @And("I store the user IDs from the response")
    public void iStoreTheUserIdsFromTheResponse() {
        List<Integer> ids = context.getResponse().jsonPath().getList("data.id");
        context.setStoredUserIds(ids);
    }

    @Then("the response body {string} array should not be empty")
    public void theResponseBodyArrayShouldNotBeEmpty(String arrayField) {
        List<?> array = context.getResponse().jsonPath().getList(arrayField);
        assertThat(array)
                .as("Response body '%s' array should not be empty", arrayField)
                .isNotEmpty();
    }

    @Then("the response body {string} array should be empty")
    public void theResponseBodyArrayShouldBeEmpty(String arrayField) {
        List<?> array = context.getResponse().jsonPath().getList(arrayField);
        assertThat(array)
                .as("Response body '%s' array should be empty", arrayField)
                .isEmpty();
    }

    @Then("the response body {string} array should contain exactly {int} item(s)")
    public void theResponseBodyArrayShouldContainExactlyItems(String arrayField, int expectedSize) {
        List<?> array = context.getResponse().jsonPath().getList(arrayField);
        assertThat(array)
                .as("Response body '%s' array size", arrayField)
                .hasSize(expectedSize);
    }

    @Then("{string} should equal the ceiling of {string} divided by {string}")
    public void shouldEqualCeilingDividedBy(String resultField, String totalField, String perPageField) {
        int total = context.getResponse().jsonPath().getInt(totalField);
        int perPage = context.getResponse().jsonPath().getInt(perPageField);
        int actualTotalPages = context.getResponse().jsonPath().getInt(resultField);
        int expectedTotalPages = (int) Math.ceil((double) total / perPage);
        assertThat(actualTotalPages)
                .as("'%s' should equal ceil(%s/%s) = %d", resultField, totalField, perPageField, expectedTotalPages)
                .isEqualTo(expectedTotalPages);
    }

    @Then("{string} should equal the value of {string}")
    public void shouldEqualValueOf(String field1, String field2) {
        int value1 = context.getResponse().jsonPath().getInt(field1);
        int value2 = context.getResponse().jsonPath().getInt(field2);
        assertThat(value1)
                .as("'%s' (%d) should equal '%s' (%d)", field1, value1, field2, value2)
                .isEqualTo(value2);
    }

    @Then("every user in the {string} array should have an integer field {string}")
    public void everyUserShouldHaveIntegerField(String arrayField, String field) {
        List<Map<String, Object>> users = context.getResponse().jsonPath().getList(arrayField);
        assertThat(users).as("'%s' array should not be empty", arrayField).isNotEmpty();
        for (Map<String, Object> user : users) {
            assertThat(user.get(field))
                    .as("User field '%s' should be an integer", field)
                    .isInstanceOf(Integer.class);
        }
    }

    @Then("every user in the {string} array should have a valid email field {string}")
    public void everyUserShouldHaveValidEmailField(String arrayField, String field) {
        List<Map<String, Object>> users = context.getResponse().jsonPath().getList(arrayField);
        assertThat(users).as("'%s' array should not be empty", arrayField).isNotEmpty();
        for (Map<String, Object> user : users) {
            String email = (String) user.get(field);
            assertThat(email)
                    .as("User field '%s' should be a valid email", field)
                    .isNotNull()
                    .isNotEmpty()
                    .contains("@");
        }
    }

    @Then("every user in the {string} array should have a non-empty string field {string}")
    public void everyUserShouldHaveNonEmptyStringField(String arrayField, String field) {
        List<Map<String, Object>> users = context.getResponse().jsonPath().getList(arrayField);
        assertThat(users).as("'%s' array should not be empty", arrayField).isNotEmpty();
        for (Map<String, Object> user : users) {
            Object value = user.get(field);
            assertThat(value)
                    .as("User field '%s' should be a non-empty string", field)
                    .isInstanceOf(String.class);
            assertThat((String) value).isNotEmpty();
        }
    }

    @Then("every user in the {string} array should have an HTTPS URL field {string}")
    public void everyUserShouldHaveHttpsUrlField(String arrayField, String field) {
        List<Map<String, Object>> users = context.getResponse().jsonPath().getList(arrayField);
        assertThat(users).as("'%s' array should not be empty", arrayField).isNotEmpty();
        for (Map<String, Object> user : users) {
            String url = (String) user.get(field);
            assertThat(url)
                    .as("User field '%s' should be an HTTPS URL", field)
                    .isNotNull()
                    .isNotEmpty()
                    .startsWith("https://");
        }
    }

    @Then("the response body {string} array size should be at most {string} users")
    public void theResponseBodyArraySizeShouldBeAtMost(String arrayField, String totalField) {
        List<?> array = context.getResponse().jsonPath().getList(arrayField);
        int total = context.getResponse().jsonPath().getInt(totalField);
        assertThat(array.size())
                .as("'%s' array size should be ≤ total (%d)", arrayField, total)
                .isLessThanOrEqualTo(total);
    }

    @Then("the response body {string} object should have a non-empty {string} field")
    public void theResponseBodyObjectShouldHaveNonEmptyField(String objectField, String field) {
        String value = context.getResponse().jsonPath().getString(objectField + "." + field);
        assertThat(value)
                .as("Response body '%s.%s' should be non-empty", objectField, field)
                .isNotNull()
                .isNotEmpty();
    }

    @Then("the user IDs from page 2 should not overlap with user IDs from page 1")
    public void theUserIdsFromPage2ShouldNotOverlapWithPage1() {
        List<Integer> page1Ids = context.getStoredUserIds();
        List<Integer> page2Ids = context.getResponse().jsonPath().getList("data.id");
        List<Integer> overlap = page2Ids.stream()
                .filter(page1Ids::contains)
                .collect(Collectors.toList());
        assertThat(overlap)
                .as("User IDs from page 1 and page 2 must not overlap, but found common IDs: %s", overlap)
                .isEmpty();
    }
}
