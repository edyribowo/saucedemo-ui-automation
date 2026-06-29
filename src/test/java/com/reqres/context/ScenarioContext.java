package com.reqres.context;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class ScenarioContext {

    private RequestSpecification requestSpec;
    private Response response;
    private List<Integer> storedUserIds;

    public RequestSpecification getRequestSpec() {
        return requestSpec;
    }

    public void setRequestSpec(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<Integer> getStoredUserIds() {
        return storedUserIds;
    }

    public void setStoredUserIds(List<Integer> storedUserIds) {
        this.storedUserIds = storedUserIds;
    }
}
