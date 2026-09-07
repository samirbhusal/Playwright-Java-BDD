package step_def.api;

import io.cucumber.java.en.Given;
import services.UserAuth;

public class UserAuthSteps {

    UserAuth userAuth = new UserAuth();

    @Given("API environment is set up")
    public void launchAPIEnvironment() {
        userAuth.launch();
    }
}
