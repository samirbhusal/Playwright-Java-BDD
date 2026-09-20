package step_def.api;

import io.cucumber.java.en.Given;
import modules.api.AbstractApiStepDefinition;

public class UserAuthSteps extends AbstractApiStepDefinition {


    @Given("API environment is set up")
    public void launchAPIEnvironment() {
        apiServices().userAuth().launch();
    }
}
