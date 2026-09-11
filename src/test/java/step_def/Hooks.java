package step_def;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

import static core.ConfigLoader.isPlatform;
import static core.PlaywrightDriverManager.*;

public class Hooks {

    @Before()
    public void setUp() {
        if (isPlatform("web")) initBrowserContextAndPage();
        else if (isPlatform("api")) initAPIRequestContext();

    }


    @AfterStep
    public void afterStep(Scenario scenario) {
        if (isPlatform("web") && scenario.isFailed() && getPage() != null) {
            try {
                byte[] screenshot = getPage().screenshot();
//                scenario.attach(screenshot, "image/png", "Failed Step View");
                Allure.addAttachment("Failed Step View", "image/png",
                        new ByteArrayInputStream(screenshot), ".png");
            } catch (Exception e) {
                // Don't let a failed screenshot attempt mask the actual scenario failure.
                System.err.println("Failed to capture screenshot for '" + scenario.getName() + "': " + e.getMessage());
            }
        }
    }

    @After
    public void tearDown() {
        if (isPlatform("web")) {
            closeContextAndPage();
            closeBrowser();
        }
        if (isPlatform("api")) closeAPIRequestContext();

        closePlaywright();
    }
}
