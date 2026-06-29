# saucedemo-ui-automation

Cucumber BDD + Playwright (Java) UI automation framework for [Swag Labs (saucedemo.com)](https://www.saucedemo.com/).

## Tech Stack

| Layer | Library |
|---|---|
| Test framework | Cucumber 7 + JUnit Platform Suite |
| Browser automation | Microsoft Playwright for Java 1.44 |
| Assertions | AssertJ |
| Build tool | Maven 3 |
| Language | Java 17 |

## Project Structure

```
src/test/
├── java/com/saucedemo/
│   ├── base/
│   │   └── BasePage.java          # Abstract BaseClass with wait-aware wrappers
│   ├── pages/
│   │   ├── LoginPage.java
│   │   ├── InventoryPage.java
│   │   ├── ProductDetailPage.java
│   │   ├── CartPage.java
│   │   ├── CheckoutPage.java
│   │   └── NavigationMenuPage.java
│   ├── steps/
│   │   ├── AuthSteps.java
│   │   ├── InventorySteps.java
│   │   ├── CartSteps.java
│   │   ├── CheckoutSteps.java
│   │   └── NavigationSteps.java
│   ├── hooks/
│   │   └── Hooks.java             # Browser lifecycle + screenshot on failure
│   └── runner/
│       └── TestRunner.java
└── resources/
    ├── features/
    │   ├── authentication.feature  # 9 scenarios
    │   ├── inventory.feature       # 6 scenarios
    │   ├── cart.feature            # 6 scenarios
    │   ├── checkout.feature        # 8 scenarios
    │   └── navigation.feature      # 2 scenarios
    └── cucumber.properties
```

## Test Coverage

| Feature | Scenarios | Tags Available |
|---|---|---|
| Authentication | 9 | `@auth`, `@smoke`, `@positive`, `@negative`, `@security` |
| Product Inventory | 6 | `@inventory`, `@smoke`, `@positive` |
| Shopping Cart | 6 | `@cart`, `@smoke`, `@positive`, `@edge` |
| Checkout | 8 | `@checkout`, `@smoke`, `@positive`, `@negative` |
| Navigation Menu | 2 | `@navigation`, `@smoke`, `@positive` |
| **Total** | **31** | |

## Running Tests

```bash
# Run all tests
mvn test

# Run only smoke tests
mvn test -Dcucumber.filter.tags="@smoke"

# Run a specific feature
mvn test -Dcucumber.filter.tags="@auth"

# Run headless (for CI)
# Set headless=true in Hooks.java or via system property
```

## Reports

After running, HTML and JSON reports are generated at:
- `target/cucumber-reports/cucumber-report.html`
- `target/cucumber-reports/cucumber-report.json`
