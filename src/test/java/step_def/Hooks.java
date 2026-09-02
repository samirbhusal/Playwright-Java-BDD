package step_def;

import core.PlaywrightDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;

public class Hooks {

    @BeforeAll
    public static void beforeSuite() {
        try {
            PlaywrightDriverManager.initPlaywright();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error initializing Playwright instance: \n" + e.getMessage());
        }
    }

    /**
     * Run before each Cucumber scenario
     * Initializes Playwright browser
     */
    @Before
    public void setUp() {
        try {
            PlaywrightDriverManager.intiBrowserContextAndPage();
        } catch (Exception e) {
            System.out.println("Error initializing Browser context and page : \n" + e.getMessage());
        }
    }

    /**
     * Run after each Cucumber scenario (success or failure)
     * Closes all Playwright resources
     */
    @After
    public void tearDown() {
        try {
            PlaywrightDriverManager.closeContextAndPage();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error closing browser context and page: \n" + e.getMessage());
        }
    }

    @AfterAll
    public static void afterSuite() {
        try {
            PlaywrightDriverManager.closePlaywrightInstance();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error closing Playwright instance: \n" + e.getMessage());
        }

    }
}
