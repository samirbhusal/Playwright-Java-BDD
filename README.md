# Playwright and Appium Java BDD

Test automation with Playwright, Appium, Cucumber, TestNG, and Allure. The framework
supports web, API, and mobile scenarios for Android and iOS.

## Stack

- Playwright Java `1.62.0`
- Appium Java Client `10.1.1`
- Cucumber `7.34.4` (cucumber-java, cucumber-testng)
- TestNG `7.12.0` (parallel execution via `data-provider-thread-count`)
- Allure `2.35.4` (allure-cucumber7-jvm + allure-maven)
- Gson `2.10.1` (config file parsing)
- Java 25

## Prerequisites

- Java 25 and Maven
- For web tests, the browser dependencies required by Playwright
- For Android tests, Appium Server with the UiAutomator2 driver and an Android
  emulator or device
- For iOS tests, macOS with Xcode, an iOS simulator or device, and Appium Server
  with the XCUITest driver

Start Appium before running a mobile scenario. The current Android and iOS
session classes use `http://127.0.0.1:4723` and contain device and app paths that
must be updated for your local setup.

## Run

From the repository root:

```sh
mvn test
```

This runs `TestRunner` (TestNG + Cucumber) against the tag configured in its
`@CucumberOptions`. Cucumber JSON and JUnit XML land in `output/`, and the
Allure HTML report is in `output/allure-report/`.

Before running, make sure the platform in
`src/test/resources/configs/Run.Config` matches the selected Cucumber tag in
`src/test/java/TestRunner.java`:

| Test type | `Run.Config` platform | Cucumber tag |
| --- | --- | --- |
| Web | `web` | `@web` |
| API | `api` | `@API-001` |
| Android | `android` | `@mobile` |
| iOS | `ios` | `@mobile` |

The checked-in `Run.android.config`, `Run.ios.config`, and `Run.api.config` files
are example configurations; `ConfigLoader` currently reads only `Run.Config`.
Copy the desired platform value into `Run.Config` before running. Android and
iOS device and app capabilities are currently set directly in
`AndroidSession.java` and `IOS_Session.java`, so update those values for your
emulator, simulator, or device. The mobile login scenario is in
`src/test/resources/features/mobile/login.feature`.

Environment URLs are in `src/test/resources/configs/Environment.config`. Web
and API scenarios use the `env` setting in `Run.Config`.

## Code layout

- `src/test/resources/features/`: Cucumber scenarios.
- `src/test/java/step_def/`: steps and scenario hooks.
- `src/test/java/pages/` and `services/`: web actions and API calls.
- `src/test/java/modules/`: platform sessions and page/service factories.
- `src/test/java/core/`: configuration, browser setup, and driver state.

## Coding principles

- Write scenarios around observable behavior, with assertions that prove the stated outcome.
- Keep step definitions thin; put UI interactions in page objects and API calls in services.
- Keep scenario state isolated. Create and release Playwright resources through the platform session and hooks.
- Use the config files for environment settings; avoid adding hardcoded URLs or mode switches.
