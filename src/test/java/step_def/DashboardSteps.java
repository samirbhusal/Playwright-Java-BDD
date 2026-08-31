package step_def;

import io.cucumber.java.en.Given;
import modules.AbstractStepDefinitions;

public class DashboardSteps extends AbstractStepDefinitions {

    @Given("user clicks the {string} button")
    public void clickTheButton(String button) {
        webPlatform.clickButton(button);
    }
}
