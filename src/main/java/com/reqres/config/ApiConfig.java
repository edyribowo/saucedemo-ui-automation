package com.reqres.config;

public class ApiConfig {

    public static final String BASE_URL = "https://reqres.in/api";
    // Set REQRES_API_KEY env var to your key from app.reqres.in/api-keys
    public static final String VALID_API_KEY = resolveApiKey();
    public static final String CONTENT_TYPE = "application/json";
    public static final String API_KEY_HEADER = "x-api-key";

    private ApiConfig() {}

    private static String resolveApiKey() {
        String key = System.getenv("REQRES_API_KEY");
        if (key == null || key.isBlank()) {
            throw new IllegalStateException(
                "REQRES_API_KEY environment variable is not set. " +
                "Get a free key at app.reqres.in/api-keys and export it before running tests."
            );
        }
        return key;
    }
}
