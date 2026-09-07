package step_def;

import core.PlaywrightDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import static core.ConfigLoader.isPlatform;

public class Hooks {

    /**
     * Run before each Cucumber scenario.
     * Playwright/Browser/Page/APIRequestContext are all ThreadLocal, so the full
     * lifecycle has to be per-scenario (per-thread) rather than suite-level —
     * a single @BeforeAll only initializes the ThreadLocal slot of whichever
     * thread happens to run it, leaving every other worker thread's slot null
     * once scenarios run in parallel (see testng.xml data-provider-thread-count).
     */
    @Before()
    public void setUp() {
        try {
            PlaywrightDriverManager.initPlaywright();
            if (isPlatform("web")) {
                PlaywrightDriverManager.intiBrowserContextAndPage();
            }
            if (isPlatform("api")) {
                PlaywrightDriverManager.initAPIRequestContext();
            }
        } catch (Exception e) {
            System.out.println("Error initializing : \n" + e.getMessage());
        }
    }

    /**
     * Run after each Cucumber scenario (success or failure).
     * Closes all Playwright resources created in setUp() for this thread.
     */
    @After
    public void tearDown() {
        try {
            PlaywrightDriverManager.closeContextAndPage();
            PlaywrightDriverManager.closePlaywrightInstance();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error closing Playwright resources: \n" + e.getMessage());
        }
    }
}
