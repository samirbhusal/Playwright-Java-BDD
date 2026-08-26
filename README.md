# Playwright-Java-BDD

Playwright Java + Cucumber BDD + TestNG test automation framework.

## Stack

- Playwright Java `1.62.0`
- Cucumber `7.33.0` (cucumber-java, cucumber-testng)
- TestNG `7.12.0`
- Gson `2.10.1` (config file parsing)
- Java 25

## Project Structure

```
src/test/java/
├── TestRunner.java              # Cucumber-TestNG entry point, glue = {step_def}
├── core/
│   ├── ConfigLoader.java        # Reads Run.Config and Environment.config
│   ├── BrowserChoice.java       # Enum: CHROME, EDGE, FIREFOX, WEBKIT
│   ├── BrowserFactory.java      # Picks a random browser from BrowserChoice and launches it
│   └── PlaywrightDriverManager.java  # Thread-local Playwright/Browser/Context/Page lifecycle
├── interfaces/
│   ├── WebActions.java          # launch()
│   ├── WebValidations.java      # (empty, placeholder)
│   └── Web_Platform.java        # extends WebActions + WebValidations
├── modules/
│   ├── WebPlatform.java         # implements Web_Platform, wraps the Playwright Page
│   └── AbstractStepDefinitions.java  # base class, holds a Web_Platform instance
├── pages/
│   └── LoginPage.java           # empty page object (WIP)
└── step_def/
    ├── Hooks.java                # @Before initBrowser(), @After closeBrowserInstance()
    ├── CommonSteps.java          # "user launches the web app", "user clicks the login button"
    └── LoginSteps.java           # login step definitions (currently just print statements)

src/test/resources/
├── configs/
│   ├── Run.Config                # platform + environment selection
│   └── Environment.config        # baseUrl per environment (qa, stg)
└── features/
    └── login.feature             # single scenario: Valid Login, tag @TES-001
```

## How it fits together

1. `TestRunner` runs Cucumber via TestNG, loading glue code from `step_def`.
2. Before each scenario, `Hooks.setUp()` calls `PlaywrightDriverManager.initBrowser()`, which:
    - reads `platform`/`env` from `Run.Config`
    - reads `baseUrl` for that env from `Environment.config`
    - picks a random browser (`BrowserFactory`) and opens it, navigating to `baseUrl`
3. Step definitions get the page via `PlaywrightDriverManager.getPage()`.
4. After each scenario, `Hooks.tearDown()` closes the page, context, browser, and Playwright instance.

## Config files

- `Run.Config` — sets which platform (`web`) and environment (`qa`/`stg`) to run against.
- `Environment.config` — maps each environment to its `baseUrl`.
- Headless mode: pass `-Dheadless=true` on the command line (defaults to headed/false).

## Running tests

```
mvn test
```

This runs `TestRunner`, which executes scenarios tagged `@TES-001` in `login.feature`.

## Current state / known gaps

- `LoginPage.java` is empty — no locators or page methods yet.
- `LoginSteps.java` step definitions only print to console, no real Playwright actions wired in.
- `WebValidations` interface is empty — no assertion helpers defined yet.
- Only one platform implementation (`WebPlatform`) exists; the `Web_Platform` interface is written for future
  implementations (mobile, API, etc.) but nothing else implements it yet.
