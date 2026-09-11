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
    ├── Hooks.java                 # @Before (context+page for web, APIRequestContext for api),
    │                              # @AfterStep (failure screenshot), @After (teardown)
    ├── web/
    │   ├── CommonSteps.java       # app launch + landing page steps
    │   ├── LoginSteps.java        # login flow steps
    │   └── DashboardSteps.java    # empty, placeholder
    └── api/
        └── UserAuthSteps.java     # API environment setup step

src/test/resources/
├── allure.properties             # allure.results.directory=output/allure-results
├── configs/
│   ├── Run.Config                # platform + environment selection (read by ConfigLoader)
│   ├── Run.api.config            # not currently read — ConfigLoader is hardcoded to Run.Config
│   └── Environment.config        # baseUrl per environment (qa, stg)
└── features/
    ├── web/login.feature         # login feature, tags @login @web @regression
    └── api/auth.feature          # registration feature, tag @API-001
```

## How it fits together

1. `TestRunner` extends `AbstractTestNGCucumberTests` and overrides `scenarios()` with
   `@DataProvider(parallel = true)` — cucumber-testng's own data provider isn't marked parallel, so
   `data-provider-thread-count` in `testng.xml` (currently `4`) is ignored without that override. Each scenario
   also runs under `RetryAnalyzer`, which retries a failure once.
2. Before each scenario, `Hooks.setUp()` (`@Before`) branches on `ConfigLoader.isPlatform(...)`:
    - `web` → `initBrowserContextAndPage()` creates `Playwright`, launches a randomly chosen browser (`BrowserFactory` +
      `BrowserChoice`), then opens a fresh `BrowserContext`/`Page`.
    - `api` → `initAPIRequestContext()` creates `Playwright` and opens an `APIRequestContext`.
      This is per-scenario, not per-suite: `Playwright`/`Browser`/`Page`/`APIRequestContext` are all `ThreadLocal`
      (see below), so a suite-level `@BeforeAll` would only populate the `ThreadLocal` slot of whichever thread ran
      it — every other parallel worker thread's slot would be `null`.
3. `ConfigLoader` reads `platform`/`env` from `Run.Config` and resolves `baseUrl` for that env from
   `Environment.config`; page objects use it to navigate.
4. Step definitions drive page/service objects (`CommonPage`, `LoginPage`, `Dashboard`, `UserAuth`), which get the
   current `Page`/`APIRequestContext` via `PlaywrightDriverManager`.
5. `Hooks.afterStep()` (`@AfterStep`) attaches a screenshot to the Allure report when a web step fails.
6. After each scenario, `Hooks.tearDown()` (`@After`) closes page/context/browser (web) or disposes the
   `APIRequestContext` (api), then closes the thread's `Playwright` instance.

All Playwright/browser/request state is thread-local, so each TestNG worker thread gets its own isolated
browser/API context, created and torn down fresh every scenario.

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

### Failure screenshots

`Hooks.afterStep()` captures a screenshot whenever a web step fails and attaches it via
`Allure.addAttachment`. In the report it appears on the `step_def.Hooks.afterStep(...)` line immediately
below the failed step, not inside the failed step itself.

That placement is inherent to attaching from a hook. `AllureCucumber7Jvm` treats `@Before`/`@After`
(`HookType.BEFORE`/`AFTER`) as *fixtures* — which is why an `@After` screenshot lands in the collapsed
"Tear Down" section — while `@AfterStep` (`HookType.AFTER_STEP`) is reported as its own sibling step.
`Allure.addAttachment` always writes to whichever step is currently open, and Cucumber closes the failed
step before any hook starts. Attaching *inside* the failed step would mean wrapping assertions in every
step definition or page object; keeping the logic in one hook is the deliberate trade-off.

## Current state / known gaps

- Random browser selection (`BrowserChoice.random()`) is a deliberate coverage strategy (sample across browsers
  over many runs instead of running every scenario 4x) — but nothing currently logs/records which browser a given
  run used, so a CI failure can't be reproduced locally without guessing.
- `BrowserChoice.EDGE` exists in the enum but `BrowserFactory` doesn't launch it yet (Playwright Edge channel not
  installed locally) — it's still selectable by `random()` and currently falls through to Chrome.
- `login.feature`'s "Valid Login" and "Invalid Login" scenarios are currently identical (same data, no outcome
  assertion) — neither actually verifies success/failure of the login yet.
- `DashboardSteps.java` is empty and `Dashboard.java` isn't wired into any feature yet. `Dashboard`'s constructor
  also takes a `Page` argument it ignores, reading from `PlaywrightDriverManager` instead.
- `Run.api.config` is unused — `ConfigLoader` is hardcoded to `Run.Config`.
- `AbstractStepDefinitions` is currently an empty marker class — step defs and page objects extend it, but it
  holds no shared behavior yet.
- `ConfigLoader.headless()` ignores CI detection despite its javadoc claiming otherwise — it returns `false`
  unless `-Dheadless` is passed explicitly.
- `TestRunner` is pinned to `tags = "@web"`, so the API feature doesn't run without editing that annotation.
- `pipelines/` exists but is empty — no CI pipeline yet. No trace/video capture yet; both on the roadmap.
