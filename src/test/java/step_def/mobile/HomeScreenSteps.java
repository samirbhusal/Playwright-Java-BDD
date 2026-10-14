package step_def.mobile;

import io.cucumber.java.en.Then;
import modules.mobile.AbstractMobileStepDefinitions;

public class HomeScreenSteps extends AbstractMobileStepDefinitions {

    @Then("user verifies the mobile home screen")
    public void landsOnMobileHomeScreen() {
        screenFactory().getHomeScreen().verifyHomeScreen();
    }
}
