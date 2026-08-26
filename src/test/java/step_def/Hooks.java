package step_def;

import core.PlaywrightDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.Arrays;

public class Hooks {

    /**
     * Run before each Cucumber scenario
     * Initializes Playwright browser
     */
    @Before
    public void setUp() {
        try {
            PlaywrightDriverManager.initBrowser();
        } catch (Exception e) {
            System.out.println("Browser not initialized: \n" + e.getMessage());
        }
    }

    /**
     * Run after each Cucumber scenario (success or failure)
     * Closes all Playwright resources
     */
    @After
    public void tearDown() {
        try {
            PlaywrightDriverManager.closeBrowserInstance();
        } catch (Exception e) {
            System.out.println("Error closing Browser instance: \n" + Arrays.toString(e.getStackTrace()));
        }
    }
}
