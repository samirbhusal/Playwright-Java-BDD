package step_def;

import core.ConfigLoader;
import helper.PlatformType;
import interfaces.PlatformSession;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import modules.PlatformFactory;

import java.io.ByteArrayInputStream;

import static core.PlaywrightDriverManager.getPage;

public class Hooks {

    PlatformSession platformSession;

    @Before()
    public void setUp() {
        platformSession = PlatformFactory.createSession(ConfigLoader.getPlatformType());
        platformSession.start();
    }


    @After
    public void tearDown(Scenario scenario) {
        if (ConfigLoader.getPlatformType() == PlatformType.WEB) {
            if (scenario.isFailed() && getPage() != null) {
                try {
                    byte[] screenshot = getPage().screenshot();
                    Allure.addAttachment("Failed Step View", "image/png",
                            new ByteArrayInputStream(screenshot), ".png");
                } catch (Exception e) {
                    System.err.println("Failed to capture screenshot for '" + scenario.getName() + "': " + e.getMessage());
                }
            }
        }
        platformSession.stop();
    }
}
