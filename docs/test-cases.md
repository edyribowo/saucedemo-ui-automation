# Test Cases — ReqRes Users API

**Service:** ReqRes (https://reqres.in)  
**Base URL:** `https://reqres.in/api`  
**Auth:** `x-api-key: reqres-free-v1` (required on all endpoints)  
**Date:** 2026-06-29

---

## Global Pre-conditions

- A valid API key is available: `reqres-free-v1`
- The API base URL is reachable
- All requests use `Content-Type: application/json` unless stated otherwise

---

## Endpoint 1: Create User — POST /api/users

---

### TC-CU-001 — Create user with valid name and job

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` <br>2. Set header `x-api-key: reqres-free-v1` <br>3. Set header `Content-Type: application/json` <br>4. Set body `{"name": "morpheus", "job": "leader"}` |
| **Test Data** | `name = "morpheus"`, `job = "leader"` |
| **Expected Result** | HTTP 201; response body contains `name = "morpheus"`, `job = "leader"`, a non-empty string `id`, and a valid ISO 8601 `createdAt` timestamp |

---

### TC-CU-002 — Create user with name only (no job field)

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` <br>2. Set required headers <br>3. Set body `{"name": "trinity"}` |
| **Test Data** | `name = "trinity"` |
| **Expected Result** | HTTP 201; response body echoes `name = "trinity"` with no `job` field; `id` and `createdAt` are present |

---

### TC-CU-003 — Create user with job only (no name field)

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` <br>2. Set required headers <br>3. Set body `{"job": "zion resident"}` |
| **Test Data** | `job = "zion resident"` |
| **Expected Result** | HTTP 201; response body echoes `job = "zion resident"` with no `name` field; `id` and `createdAt` are present |

---

### TC-CU-004 — Create user with empty JSON body

| Field | Detail |
|---|---|
| **Type** | Positive / Edge Case |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` <br>2. Set required headers <br>3. Set body `{}` |
| **Test Data** | `{}` |
| **Expected Result** | HTTP 201; response body still contains a generated `id` and `createdAt`; no `name` or `job` fields are present in the response |

---

### TC-CU-005 — Create user with additional unexpected fields

| Field | Detail |
|---|---|
| **Type** | Positive / Edge Case |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` <br>2. Set required headers <br>3. Set body `{"name": "neo", "job": "the one", "age": 30, "active": true}` |
| **Test Data** | `name = "neo"`, `job = "the one"`, `age = 30`, `active = true` |
| **Expected Result** | HTTP 201; all sent fields (`name`, `job`, `age`, `active`) are echoed back in the response alongside `id` and `createdAt` |

---

### TC-CU-006 — Create user without API key header

| Field | Detail |
|---|---|
| **Type** | Negative |
| **Pre-conditions** | API service is available |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` <br>2. Set header `Content-Type: application/json` — **omit** `x-api-key` <br>3. Set body `{"name": "morpheus", "job": "leader"}` |
| **Test Data** | `name = "morpheus"`, `job = "leader"` |
| **Expected Result** | HTTP 401; response body contains `{"error": "Missing API key.", "how_to_get_one": "https://reqres.in/signup"}` |

---

### TC-CU-007 — Create user with invalid API key

| Field | Detail |
|---|---|
| **Type** | Negative |
| **Pre-conditions** | API service is available |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` <br>2. Set header `x-api-key: invalid-key-xyz` <br>3. Set header `Content-Type: application/json` <br>4. Set body `{"name": "morpheus", "job": "leader"}` |
| **Test Data** | `x-api-key = "invalid-key-xyz"` |
| **Expected Result** | HTTP 401; response body indicates an invalid or missing API key error |

---

### TC-CU-008 — Create user with special characters in name and job

| Field | Detail |
|---|---|
| **Type** | Edge Case |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` <br>2. Set required headers <br>3. Set body `{"name": "Ñoño O'Brien", "job": "Dev & QA <Lead>"}` |
| **Test Data** | `name = "Ñoño O'Brien"`, `job = "Dev & QA <Lead>"` |
| **Expected Result** | HTTP 201; response body echoes `name` and `job` exactly as sent (including special characters); `id` and `createdAt` are present |

---

### TC-CU-009 — Create user with numeric string values

| Field | Detail |
|---|---|
| **Type** | Edge Case |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` <br>2. Set required headers <br>3. Set body `{"name": "12345", "job": "007"}` |
| **Test Data** | `name = "12345"`, `job = "007"` |
| **Expected Result** | HTTP 201; `name` and `job` are echoed as strings; `id` and `createdAt` are present |

---

### TC-CU-010 — Create user with very long string values (boundary)

| Field | Detail |
|---|---|
| **Type** | Edge Case |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` <br>2. Set required headers <br>3. Set body where `name` is a string of 1000 characters and `job` is a string of 1000 characters |
| **Test Data** | `name = "a" * 1000`, `job = "b" * 1000` |
| **Expected Result** | HTTP 201 and values are echoed back; OR HTTP 4xx if the API enforces a field length limit — response must not be an unhandled server error (5xx) |

---

### TC-CU-011 — Verify response id field type is string

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` with `{"name": "morpheus", "job": "leader"}` and required headers <br>2. Inspect the `id` field in the response body |
| **Test Data** | `name = "morpheus"`, `job = "leader"` |
| **Expected Result** | HTTP 201; `id` is present and its JSON type is `string` (e.g. `"482"`, not `482`) |

---

### TC-CU-012 — Verify createdAt is a valid ISO 8601 timestamp

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` with `{"name": "morpheus", "job": "leader"}` and required headers <br>2. Inspect the `createdAt` field in the response body |
| **Test Data** | `name = "morpheus"`, `job = "leader"` |
| **Expected Result** | HTTP 201; `createdAt` matches the ISO 8601 date-time format (e.g. `"2025-06-26T09:12:44.114Z"`); the timestamp is close to the current UTC time (within a reasonable tolerance) |

---

### TC-CU-013 — Create user with null field values

| Field | Detail |
|---|---|
| **Type** | Edge Case |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `POST https://reqres.in/api/users` <br>2. Set required headers <br>3. Set body `{"name": null, "job": null}` |
| **Test Data** | `name = null`, `job = null` |
| **Expected Result** | HTTP 201 with `null` values echoed back; OR HTTP 4xx with a descriptive error — must not return HTTP 5xx |

---

## Endpoint 2: List Users (Paginated) — GET /api/users

---

### TC-LU-001 — List users with default parameters (page 1)

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users` <br>2. Set header `x-api-key: reqres-free-v1` |
| **Test Data** | No query params |
| **Expected Result** | HTTP 200; `page = 1`, `per_page = 6`; `data` array contains up to 6 user objects; `total` and `total_pages` are positive integers; `support` object is present |

---

### TC-LU-002 — List users on page 2

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=2` <br>2. Set header `x-api-key: reqres-free-v1` |
| **Test Data** | `page = 2` |
| **Expected Result** | HTTP 200; `page = 2`; `data` array contains user objects (up to `per_page`); user IDs are different from page 1 results |

---

### TC-LU-003 — List users with custom per_page

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?per_page=3` <br>2. Set header `x-api-key: reqres-free-v1` |
| **Test Data** | `per_page = 3` |
| **Expected Result** | HTTP 200; `per_page = 3`; `data` array contains exactly 3 user objects (if total > 3); `total_pages = ceil(total / 3)` |

---

### TC-LU-004 — Verify all pagination metadata fields

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=1&per_page=6` <br>2. Inspect all top-level fields in the response |
| **Test Data** | `page = 1`, `per_page = 6` |
| **Expected Result** | HTTP 200; response contains `page` (integer), `per_page` (integer), `total` (integer > 0), `total_pages` (integer > 0); `total_pages == ceil(total / per_page)` |

---

### TC-LU-005 — Verify user object schema in data array

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=1` <br>2. Inspect each element in the `data` array |
| **Test Data** | `page = 1` |
| **Expected Result** | HTTP 200; every object in `data` contains `id` (integer), `email` (valid email string), `first_name` (string), `last_name` (string), `avatar` (URL string starting with `https://`) |

---

### TC-LU-006 — List users without API key header

| Field | Detail |
|---|---|
| **Type** | Negative |
| **Pre-conditions** | API service is available |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=1` — **omit** `x-api-key` header |
| **Test Data** | No API key |
| **Expected Result** | HTTP 401; response body contains `{"error": "Missing API key.", "how_to_get_one": "https://reqres.in/signup"}` |

---

### TC-LU-007 — List users with invalid API key

| Field | Detail |
|---|---|
| **Type** | Negative |
| **Pre-conditions** | API service is available |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=1` <br>2. Set header `x-api-key: totally-wrong-key` |
| **Test Data** | `x-api-key = "totally-wrong-key"` |
| **Expected Result** | HTTP 401; response body indicates an invalid API key error |

---

### TC-LU-008 — List users with page = 0 (below minimum boundary)

| Field | Detail |
|---|---|
| **Type** | Edge Case |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=0` <br>2. Set header `x-api-key: reqres-free-v1` |
| **Test Data** | `page = 0` |
| **Expected Result** | HTTP 200 returning page 1 data (API defaults to page 1) OR HTTP 400 with a descriptive error; must not return HTTP 5xx |

---

### TC-LU-009 — List users with page beyond total_pages (out-of-range boundary)

| Field | Detail |
|---|---|
| **Type** | Edge Case |
| **Pre-conditions** | API service is available; valid API key is set; `total_pages` is known (e.g. 2) |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=9999` <br>2. Set header `x-api-key: reqres-free-v1` |
| **Test Data** | `page = 9999` |
| **Expected Result** | HTTP 200 with an empty `data` array (`[]`); pagination metadata (`total`, `total_pages`) still reflects the full dataset; must not return HTTP 5xx |

---

### TC-LU-010 — List users with negative page number

| Field | Detail |
|---|---|
| **Type** | Edge Case |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=-1` <br>2. Set header `x-api-key: reqres-free-v1` |
| **Test Data** | `page = -1` |
| **Expected Result** | HTTP 400 with a descriptive error; OR HTTP 200 returning page 1 data; must not return HTTP 5xx |

---

### TC-LU-011 — List users with per_page = 1 (minimum single result)

| Field | Detail |
|---|---|
| **Type** | Edge Case |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?per_page=1` <br>2. Set header `x-api-key: reqres-free-v1` |
| **Test Data** | `per_page = 1` |
| **Expected Result** | HTTP 200; `data` array contains exactly 1 user; `per_page = 1` in the response; `total_pages` equals `total` |

---

### TC-LU-012 — List users with per_page greater than total users

| Field | Detail |
|---|---|
| **Type** | Edge Case |
| **Pre-conditions** | API service is available; valid API key is set; `total` users is known (12) |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?per_page=100` <br>2. Set header `x-api-key: reqres-free-v1` |
| **Test Data** | `per_page = 100` |
| **Expected Result** | HTTP 200; `data` array contains all users (≤ total); `total_pages = 1`; must not return HTTP 5xx |

---

### TC-LU-013 — Verify avatar URLs are reachable HTTPS links

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=1` <br>2. For each user in `data`, extract the `avatar` field value <br>3. Verify the URL format |
| **Test Data** | `page = 1` |
| **Expected Result** | Every `avatar` value is a non-empty string starting with `https://`; optionally verify HTTP GET on each avatar URL returns 200 |

---

### TC-LU-014 — Verify support object is present and well-formed

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=1` <br>2. Inspect the `support` object in the response |
| **Test Data** | `page = 1` |
| **Expected Result** | HTTP 200; `support` object contains `url` (a non-empty string) and `text` (a non-empty string) |

---

### TC-LU-015 — Verify page 1 and page 2 return non-overlapping users

| Field | Detail |
|---|---|
| **Type** | Positive |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=1` and collect all `id` values from `data` <br>2. Send `GET https://reqres.in/api/users?page=2` and collect all `id` values from `data` <br>3. Compare the two sets of IDs |
| **Test Data** | `page = 1`, `page = 2` |
| **Expected Result** | HTTP 200 for both requests; the `id` sets from page 1 and page 2 have no common elements (zero intersection) |

---

### TC-LU-016 — List users with non-numeric page parameter

| Field | Detail |
|---|---|
| **Type** | Negative / Edge Case |
| **Pre-conditions** | API service is available; valid API key is set |
| **Test Steps** | 1. Send `GET https://reqres.in/api/users?page=abc` <br>2. Set header `x-api-key: reqres-free-v1` |
| **Test Data** | `page = "abc"` |
| **Expected Result** | HTTP 400 with a descriptive error message; OR HTTP 200 defaulting to page 1; must not return HTTP 5xx |

---

## Summary

| Test Case ID | Endpoint | Type | Coverage Area |
|---|---|---|---|
| TC-CU-001 | POST /api/users | Positive | Full valid payload |
| TC-CU-002 | POST /api/users | Positive | Name only |
| TC-CU-003 | POST /api/users | Positive | Job only |
| TC-CU-004 | POST /api/users | Edge Case | Empty body |
| TC-CU-005 | POST /api/users | Edge Case | Extra fields echoed |
| TC-CU-006 | POST /api/users | Negative | Missing API key |
| TC-CU-007 | POST /api/users | Negative | Invalid API key |
| TC-CU-008 | POST /api/users | Edge Case | Special characters |
| TC-CU-009 | POST /api/users | Edge Case | Numeric string values |
| TC-CU-010 | POST /api/users | Edge Case | Max-length strings |
| TC-CU-011 | POST /api/users | Positive | `id` is string type |
| TC-CU-012 | POST /api/users | Positive | `createdAt` ISO 8601 format |
| TC-CU-013 | POST /api/users | Edge Case | Null field values |
| TC-LU-001 | GET /api/users | Positive | Default pagination |
| TC-LU-002 | GET /api/users | Positive | Explicit page 2 |
| TC-LU-003 | GET /api/users | Positive | Custom per_page |
| TC-LU-004 | GET /api/users | Positive | Pagination metadata schema |
| TC-LU-005 | GET /api/users | Positive | User object schema |
| TC-LU-006 | GET /api/users | Negative | Missing API key |
| TC-LU-007 | GET /api/users | Negative | Invalid API key |
| TC-LU-008 | GET /api/users | Edge Case | page = 0 (below boundary) |
| TC-LU-009 | GET /api/users | Edge Case | page beyond total_pages |
| TC-LU-010 | GET /api/users | Edge Case | Negative page number |
| TC-LU-011 | GET /api/users | Edge Case | per_page = 1 |
| TC-LU-012 | GET /api/users | Edge Case | per_page > total users |
| TC-LU-013 | GET /api/users | Positive | Avatar URL format |
| TC-LU-014 | GET /api/users | Positive | Support object schema |
| TC-LU-015 | GET /api/users | Positive | No overlapping pages |
| TC-LU-016 | GET /api/users | Negative | Non-numeric page param |
