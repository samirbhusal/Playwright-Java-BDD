# Playwright-Java-BDD

Playwright Java + Cucumber BDD + TestNG test automation framework, running scenarios in parallel via TestNG's
data-provider thread pool, with Allure reporting.

## Stack

- Playwright Java `1.62.0`
- Cucumber `7.34.4` (cucumber-java, cucumber-testng)
- TestNG `7.12.0` (parallel execution via `data-provider-thread-count`)
- Allure `2.35.4` (allure-cucumber7-jvm + allure-maven)
- Gson `2.10.1` (config file parsing)
- Java 25

## Project Structure

```
src/test/java/
├── TestRunner.java              # TestNG/Cucumber entry point, glue = {step_def}, currently tagged @web
├── core/
│   ├── ConfigLoader.java        # Reads Run.Config and Environment.config
│   ├── BrowserChoice.java       # Enum: CHROME, EDGE, FIREFOX, WEBKIT
│   ├── BrowserFactory.java      # Picks a random browser from BrowserChoice and launches it
│   ├── PlaywrightDriverManager.java  # Thread-local Playwright/Browser/Context/Page/APIRequestContext lifecycle
│   └── RetryAnalyzer.java       # TestNG IRetryAnalyzer, retries a failed scenario once
├── interfaces/
│   └── CommonActions.java       # launch() — implemented by CommonPage (web) and UserAuth (api)
├── modules/
│   └── AbstractStepDefinitions.java  # base class step defs/page/service objects extend
├── pages/
│   ├── CommonPage.java           # launch(), clickButton(), verifyUrl()
│   ├── LoginPage.java            # login page locators + actions
│   └── Dashboard.java            # post-login dashboard assertions (not yet wired into a feature)
├── services/
│   └── UserAuth.java             # API equivalent of a page object; launch() hits a health-check request
└── step_def/
    ├── Hooks.java                 # @BeforeAll/@AfterAll (Playwright instance), @Before/@After
    │                              # (context+page for web, APIRequestContext for api)
    ├── web/
    │   ├── CommonSteps.java       # app launch + landing page steps
    │   ├── LoginSteps.java        # login flow steps
    │   └── DashboardSteps.java    # empty, placeholder
    └── api/
        └── UserAuthSteps.java     # API environment setup step

src/test/resources/
├── configs/
│   ├── Run.Config                # platform + environment selection (read by ConfigLoader)
│   ├── Run.api.config            # not currently read — ConfigLoader is hardcoded to Run.Config
│   └── Environment.config        # baseUrl per environment (qa, stg)
└── features/
    ├── login.feature             # login feature, tags @login @web @regression
    └── api/auth.feature          # registration feature, tag @API-001
```

## How it fits together

1. `TestRunner` extends `AbstractTestNGCucumberTests`; `testng.xml` sets `data-provider-thread-count` to run
   scenarios in parallel across threads.
2. Once per suite, `Hooks.beforeSuite()` calls `PlaywrightDriverManager.initPlaywright()`, which just creates the
   `Playwright` instance (no browser yet).
3. Before each scenario, `Hooks.setUp()` branches on `ConfigLoader.isPlatform(...)`:
   - `web` → `intiBrowserContextAndPage()` launches a randomly chosen browser (`BrowserFactory` + `BrowserChoice`)
     and opens a fresh `BrowserContext`/`Page`.
   - `api` → `initAPIRequestContext()` opens a Playwright `APIRequestContext`.
4. `ConfigLoader` reads `platform`/`env` from `Run.Config` and resolves `baseUrl` for that env from
   `Environment.config`; page objects use it to navigate.
5. Step definitions drive page/service objects (`CommonPage`, `LoginPage`, `Dashboard`, `UserAuth`), which get the
   current `Page`/`APIRequestContext` via `PlaywrightDriverManager`.
6. After each scenario, `Hooks.tearDown()` closes the context/page; after the suite, `Hooks.afterSuite()` closes
   the browser and Playwright instance.

All Playwright/browser/request state is thread-local, so each TestNG worker thread gets its own isolated
browser/API context.

## Config files

- `Run.Config` — sets which platform (`web`/`api`) and environment (`qa`/`stg`) to run against. This is the only
  file `ConfigLoader` actually reads; `Run.api.config` is currently unused.
- `Environment.config` — maps each environment to its `baseUrl`.
- Headless mode: pass `-Dheadless=true` on the command line (defaults to headed/false).
- Config is read from a path relative to the working directory (`src/test/resources/configs/...`), not the
  classpath — only reliable when run via `mvn test` from the repo root.

## Running tests

```
mvn test
```

This runs `TestRunner` (TestNG + Cucumber) against whichever `tags` value is set in `@CucumberOptions`. Cucumber
JSON and JUnit XML land in `output/`, and Allure results are written to `output/allure-results`.

## Reports

Allure results generate during `mvn test` (via `allure-maven`). To view the report:

```
mvn allure:report   # writes HTML to output/allure-report
mvn allure:serve     # builds and opens a live report
```

## Current state / known gaps

- Random browser selection (`BrowserChoice.random()`) is a deliberate coverage strategy (sample across browsers
  over many runs instead of running every scenario 4x) — but nothing currently logs/records which browser a given
  run used, so a CI failure can't be reproduced locally without guessing.
- `BrowserChoice.EDGE` exists in the enum but `BrowserFactory` doesn't launch it yet (Playwright Edge channel not
  installed locally) — it's still selectable by `random()` and currently falls through to Chrome.
- `login.feature`'s "Valid Login" and "Invalid Login" scenarios are currently identical (same data, no outcome
  assertion) — neither actually verifies success/failure of the login yet.
- `DashboardSteps.java` and `Dashboard.java` exist but aren't wired into any feature yet.
- `Run.api.config` is unused — `ConfigLoader` is hardcoded to `Run.Config`.
- No CI pipeline yet; no failure screenshots/trace/video capture yet. Both on the roadmap.
