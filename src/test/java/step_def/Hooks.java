package step_def;

import io.cucumber.java.After;
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


    @After
    public void tearDown(Scenario scenario) {
        if (isPlatform("web")) {
            if (scenario.isFailed() && getPage() != null) {
                try {
                    byte[] screenshot = getPage().screenshot();
                    Allure.addAttachment("Failed Step View", "image/png",
                            new ByteArrayInputStream(screenshot), ".png");
                } catch (Exception e) {
                    System.err.println("Failed to capture screenshot for '" + scenario.getName() + "': " + e.getMessage());
                }
            }
            closeContextAndPage();
            closeBrowser();
        }
        if (isPlatform("api")) closeAPIRequestContext();

        closePlaywright();
    }
}
