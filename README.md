# Playwright-Java-BDD

Playwright Java + Cucumber BDD + TestNG test automation framework, running scenarios in parallel via Courgette,
with Allure reporting.

## Stack

- Playwright Java `1.62.0`
- Cucumber `7.34.4` (cucumber-java, cucumber-testng)
- TestNG `7.12.0`
- Courgette-JVM `6.22.0` (parallel scenario execution on top of TestNG)
- Allure `2.35.4` (allure-cucumber7-jvm + allure-maven)
- Gson `2.10.1` (config file parsing)
- Java 25

## Project Structure

```
src/test/java/
├── TestRunner.java              # Courgette/TestNG entry point, glue = {step_def}, runs @TES-001
├── core/
│   ├── ConfigLoader.java        # Reads Run.Config and Environment.config
│   ├── BrowserChoice.java       # Enum: CHROME, EDGE, FIREFOX, WEBKIT
│   ├── BrowserFactory.java      # Picks a random browser from BrowserChoice and launches it
│   └── PlaywrightDriverManager.java  # Thread-local Playwright/Browser/Context/Page lifecycle
├── interfaces/
│   ├── WebActions.java          # launch(), clickButton() — not yet implemented anywhere
│   └── WebValidations.java      # verifyLandingPage(), verifyUrl() — not yet implemented anywhere
├── modules/
│   └── AbstractStepDefinitions.java  # base class step defs extend, exposes PlaywrightDriverManager
├── pages/
│   ├── CommonPage.java           # launch(), clickButton(), verifyUrl()
│   ├── LoginPage.java            # login page locators + actions
│   └── Dashboard.java            # post-login dashboard assertions
└── step_def/
    ├── Hooks.java                 # @BeforeAll/@AfterAll (Playwright instance), @Before/@After (context+page)
    ├── CommonSteps.java           # app launch + landing page steps
    ├── LoginSteps.java            # login flow steps
    └── DashboardSteps.java        # empty, placeholder

src/test/resources/
├── configs/
│   ├── Run.Config                # platform + environment selection
│   └── Environment.config        # baseUrl per environment (qa, stg)
└── features/
    └── login.feature             # login feature, tag @TES-001
```

## How it fits together

1. `TestRunner` runs Cucumber through Courgette on TestNG (`runLevel = SCENARIO`), loading glue code from `step_def`.
2. Once per thread/suite, `Hooks.beforeSuite()` calls `PlaywrightDriverManager.initPlaywright()`, which creates a
   `Playwright` instance and launches a randomly chosen browser (`BrowserFactory` + `BrowserChoice`).
3. Before each scenario, `Hooks.setUp()` calls `intiBrowserContextAndPage()` to open a fresh `BrowserContext`/`Page`.
4. `ConfigLoader` reads `platform`/`env` from `Run.Config` and resolves `baseUrl` for that env from
   `Environment.config`; page objects use it to navigate.
5. Step definitions drive page objects (`CommonPage`, `LoginPage`, `Dashboard`), which get the current `Page` via
   `PlaywrightDriverManager.getPage()`.
6. After each scenario, `Hooks.tearDown()` closes the context and page; after the suite, `Hooks.afterSuite()` closes
   the browser and Playwright instance.

All Playwright/browser state is thread-local, so each Courgette thread gets its own isolated browser.

## Config files

- `Run.Config` — sets which platform (`web`) and environment (`qa`/`stg`) to run against.
- `Environment.config` — maps each environment to its `baseUrl`.
- Headless mode: pass `-Dheadless=true` on the command line (defaults to headed/false).

## Running tests

```
mvn test
```

This runs `TestRunner` (Courgette + TestNG), executing scenarios tagged `@TES-001` in `login.feature`. Cucumber JSON
and JUnit XML land in `output/`, and Allure results are written to `output/allure-results`.

## Reports

Allure results generate during `mvn test` (via `allure-maven`). To view the report:

```
mvn allure:report   # writes HTML to output/allure-report
mvn allure:serve     # builds and opens a live report
```

## Current state / known gaps

- `WebActions` / `WebValidations` interfaces exist but nothing implements them yet — page objects have similar
  methods directly, not through the interfaces.
- `DashboardSteps.java` is empty — dashboard step definitions still live in `LoginSteps.java`.
- `BrowserChoice.EDGE` exists but `BrowserFactory` doesn't launch it yet (commented out).
- The "land in dashboard" scenario steps are commented out in `login.feature`; the login flow currently stops after
  entering credentials.
