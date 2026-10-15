package step_def.mobile;

import io.cucumber.java.en.Given;
import modules.mobile.AbstractMobileStepDefinitions;

public class CommonSteps extends AbstractMobileStepDefinitions {

    @Given("user launches the mobile app")
    public void launchMobileApp() {
        screenFactory().getLoginScreen().launch();
    }
}
