# 🧪 Test Automation Framework – UI & API

Automation framework covering **UI and API testing**, designed to be **maintainable, scalable, and CI-ready**.

---

## 📌 Tech Stack

| Layer | Tools |
|------|------|
| Language | Java 17 |
| UI Automation | Selenium WebDriver |
| API Automation | RestAssured |
| Test Framework | JUnit 5 |
| Assertions | AssertJ |
| Build Tool | Maven |
| Config | YAML |
| CI/CD | GitHub Actions |
| Reporting | Allure Report |

---

## ▶️ How to Run Tests

### Run All Tests
```bash
mvn clean test
```

### Run Only UI Tests
```bash
mvn test -Pui
```

### Run Only API Tests
```bash
mvn test -Papi
```

### Run Tests by Tag Directly
```bash
mvn test -Djunit.jupiter.tags.include=ui
mvn test -Djunit.jupiter.tags.include=api
```

### Run Tests with Docker
Build the Docker image:

```bash
docker build -t ui-api-test-automation .
```

Run all tests:

```bash
docker run --rm ui-api-test-automation
```

Run only API tests:

```bash
docker run --rm ui-api-test-automation mvn -B test -Papi
```

Run only UI tests:

```bash
docker run --rm ui-api-test-automation mvn -B test -Pui
```

Open a terminal inside the Docker container:

```bash
docker run --rm -it ui-api-test-automation bash
```

The project is available inside the container at `/workspace`. From there, run:

```bash
mvn -B test -Pui
mvn -B test -Papi
mvn -B test -Pall
```

If `bash` is not available, use:

```bash
docker run --rm -it ui-api-test-automation sh
```

If proxy credentials are required, pass them as environment variables:

```bash
docker run --rm \
  -e WEBSHARE_USER="$WEBSHARE_USER" \
  -e WEBSHARE_PASS="$WEBSHARE_PASS" \
  ui-api-test-automation
```

### Browser Setup for UI Tests
UI tests require a local Chrome or Chromium browser executable.

The framework auto-detects common Chrome/Chromium locations on macOS and Linux. If Chrome is installed in a custom location, pass the binary explicitly:

```bash
mvn test -Pui -Dchrome.binary="/path/to/chrome"
```

Or use an environment variable:

```bash
export CHROME_BINARY="/path/to/chrome"
mvn test -Pui
```

On macOS, the default Google Chrome path is usually:

```bash
/Applications/Google Chrome.app/Contents/MacOS/Google Chrome
```

If you only want to run API tests, Chrome is not required:

```bash
mvn test -Papi
```

When Chrome/Chromium is not available, UI tests are skipped with a setup message instead of failing with Selenium's `cannot find Chrome binary` session error.

### Proxy Setup
For local runs, proxy setup is optional.
In CI, the proxy credentials are provided via **environment variables**.
The code expects:
```java
.withAuth(System.getenv("WEBSHARE_USER"), System.getenv("WEBSHARE_PASS"))
```

### Generate Allure Report Locally
```bash
mvn allure:report
mvn allure:serve
```

All test executions generate an Allure report during the GitHub Actions pipeline.

Live report:
https://fidandurgut.github.io/ui-api-test-automation/

Report includes:
- Test execution summary
- Passed/failed/broken tests
- Detailed steps and logs
- API request/response validation details

### Continuous Integration
Tests run automatically on:
- Every push to `main`
- Every pull request

Pipeline stages:
- Build project
- Execute UI & API tests
- Generate Allure report
- Publish report to GitHub Pages on pushes to `main`

---

## 🧱 Project Structure

```text
src/test/java/com/testautomation/
  core/
    config/
    driver/
    extensions/
    utils/
  ui/
    pages/
    tests/
  api/
    clients/
    dto/
    tests/

src/test/resources/
  environments/
  testdata/
```

All reusable automation code lives under `src/test/java` because this repository is a test automation project, not an application library.

---

# 🌐 UI Test Coverage (SauceDemo)

Application: https://www.saucedemo.com  
Test user: `standard_user / secret_sauce`

## ✔ Implemented Scenarios

| Requirement | Test Method | Validation Performed |
|------------|-------------|----------------------|
| Full checkout with at least 2 items | `CheckoutTest.userCanCheckoutWithAtLeastTwoItems_andTotalPriceIsCorrect` | Adds ≥2 items, completes checkout, validates **Item Total + Tax = Total** |
| Sort items by name Z → A | `SortingTest.shouldSortItemsByNameZtoA` | Applies Z-A sorting and verifies product names are in reverse alphabetical order |
| Failed login validation | `FailedLoginTest.shouldShowErrorMessageOnFailedLogin` | Invalid login shows error message and user remains on login page |

## UI Validation Strategy
- Page state validation after navigation
- Cart badge reflects correct number of items
- Checkout confirmation message is displayed
- Price calculation consistency is verified numerically (not string compare)
- Sorting is validated against expected alphabetical order
- Error message visibility is validated on failed login

---

# 🔌 API Test Coverage (FakeStore API)

Base URL: https://fakestoreapi.com

## ✔ Implemented Scenarios

| Requirement | Test Method | Validation Performed |
|------------|-------------|----------------------|
| Successful login | `AuthApiTest.shouldLoginAndReturnToken` | 200 response, JSON, non-empty token |
| Get product and validate content | `ProductsApiTest.shouldGetProductById` | Required fields exist, types correct, constraints checked (e.g. price ≥ 0) |
| Create cart with existing product | `CartsApiTest.shouldCreateCart` | Cart created, id returned, correct product + quantity |
| Delete a user | `UsersApiTest.shouldDeleteUser` | DELETE succeeds, response contract validated |
| Negative login | `AuthApiTest.negative_shouldRejectInvalidLogin` | Invalid login returns error, no token issued |
| Negative unknown product | `ProductsApiTest.negative_shouldReturnNoProductForUnknownProduct` | FakeStore returns 200 with an empty or `null` body for a non-existing product |

## API Validation Strategy
- HTTP status code checks
- Content-type validation
- Contract-level field presence and type validation
- Value constraints (e.g. price ≥ 0)
- Identifier consistency in create operations
- No token returned on failed authentication

---

## ⚠️ Assumptions

### UI
- SauceDemo product catalog and user credentials are stable
- Prices do not change during execution
- Sorting operates only on visible inventory items

### API
- FakeStore API is a demo service and may not persist data reliably
- Create endpoints may return 200 or 201
- Delete operations may not be reflected in subsequent GET calls
- Unknown product lookup returns 200 with an empty or `null` body instead of a conventional 404

These behaviors are treated as system limitations, not test failures.

---

## 🧱 Framework Design Highlights

- Page Object Model for UI
- API client layer for service abstraction
- Environment-based configuration
- Single base package: `com.testautomation`
- Deterministic tests (no cross-test dependency)
- CI-ready execution
- Allure reporting

---

## 🚀 Future Improvements
- JSON schema validation
- Parallel execution
- Cross browser testing

---

# 📦 Repository Notes

## Repository Layout
- Maintained as a single GitHub repository
- Contains UI tests, API tests, CI config, and documentation

## Single Project Structure
- UI and API tests are part of one Maven project
- Logical separation is done via JUnit tags, not multiple projects

## Passing Pipelines
- CI pipeline runs tests automatically
- Browser dependencies are installed during pipeline execution
- Tests run headlessly and pass without manual steps

## Informative README
This README includes:
- Tech stack and framework design
- Instructions to run tests
- UI and API requirement traceability
- Validation strategy
- Assumptions and limitations

## Test Reports
- Maven Surefire generates JUnit XML reports
- UI failures capture screenshots automatically
- Reports and screenshots are available under the `target/` directory and CI artifacts

---

# 🤖 AI Usage Disclosure

AI tools (ChatGPT) were used as an assistant for:
- Structuring documentation
- Improving wording and clarity
- Refining explanations of the framework and tests

All implementation decisions and test logic were reviewed and validated manually.

---

# 📌 Assumptions Summary

## UI
- Demo credentials remain valid
- Product data remains stable
- UI flows behave consistently

## API
- Demo API may not persist data
- Some endpoints may return multiple success codes
- Error payloads may not be consistent
- Application returns responses with correct code
- Product IDs in FakeStore are within a small fixed range; therefore a large ID (e.g., 9999) is used to simulate a non-existing product.
- FakeStore returns 200 with an empty or `null` body for that non-existing product lookup.

---

## 🙏 Thank You

Thank you for taking the time to review this project.
The structure, clarity, and validation depth are intended to demonstrate practical UI/API automation design.
