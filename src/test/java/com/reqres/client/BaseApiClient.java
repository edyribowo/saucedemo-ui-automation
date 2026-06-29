package com.reqres.client;

import com.reqres.config.ApiConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

/**
 * Central wrapper that builds RequestSpecification with consistent headers and base URI.
 * All step definitions obtain their spec through this class so auth/base-URL handling
 * is never duplicated across step definition files.
 */
public class BaseApiClient {

    public RequestSpecification withValidApiKey() {
        return new RequestSpecBuilder()
                .setBaseUri(ApiConfig.BASE_URL)
                .setContentType(ApiConfig.CONTENT_TYPE)
                .addHeader(ApiConfig.API_KEY_HEADER, ApiConfig.VALID_API_KEY)
                .log(LogDetail.ALL)
                .build();
    }

    public RequestSpecification withoutApiKey() {
        return new RequestSpecBuilder()
                .setBaseUri(ApiConfig.BASE_URL)
                .setContentType(ApiConfig.CONTENT_TYPE)
                .log(LogDetail.ALL)
                .build();
    }

    public RequestSpecification withApiKey(String apiKey) {
        return new RequestSpecBuilder()
                .setBaseUri(ApiConfig.BASE_URL)
                .setContentType(ApiConfig.CONTENT_TYPE)
                .addHeader(ApiConfig.API_KEY_HEADER, apiKey)
                .log(LogDetail.ALL)
                .build();
    }
}
