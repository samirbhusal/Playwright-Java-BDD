# Playwright Java BDD

Test automation with Playwright, Cucumber, TestNG, and Allure. TestNG runs Cucumber
scenarios in parallel; each web scenario gets its own browser context and page.

## Stack

- Playwright Java `1.62.0`
- Cucumber `7.34.4` (cucumber-java, cucumber-testng)
- TestNG `7.12.0` (parallel execution via `data-provider-thread-count`)
- Allure `2.35.4` (allure-cucumber7-jvm + allure-maven)
- Gson `2.10.1` (config file parsing)
- Java 25

## Run

From the repository root:

```sh
mvn test
```

This runs `TestRunner` (TestNG + Cucumber) against whichever `tags` value is set in `@CucumberOptions`. Cucumber
JSON and JUnit XML land in `output/`, and the Allure HTML report is in `output/allure-report/`.

Set the platform, environment, and headless mode in `src/test/resources/configs/Run.Config`.
Environment URLs are in `src/test/resources/configs/Environment.config`. To run API
scenarios, select `@API-001` in `TestRunner` and set the platform to `api` in `Run.Config`.

## Code layout

- `src/test/resources/features/`: Cucumber scenarios.
- `src/test/java/step_def/`: steps and scenario hooks.
- `src/test/java/pages/` and `services/`: web actions and API calls.
- `src/test/java/modules/`: platform sessions and page/service factories.
- `src/test/java/core/`: configuration, browser setup, and Playwright state.

## Coding principles

- Write scenarios around observable behavior, with assertions that prove the stated outcome.
- Keep step definitions thin; put UI interactions in page objects and API calls in services.
- Keep scenario state isolated. Create and release Playwright resources through the platform session and hooks.
- Use the config files for environment settings; avoid adding hardcoded URLs or mode switches.
