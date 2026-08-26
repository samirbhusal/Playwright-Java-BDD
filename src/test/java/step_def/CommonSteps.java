package step_def;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import modules.AbstractStepDefinitions;

public class CommonSteps extends AbstractStepDefinitions {

    public CommonSteps() {
        super();
    }

    @Given("user launches the web app")
    public void launchWebApp() {
        webPlatform.launch();
    }

    @When("user clicks the login button")
    public void clickLoginButton() {
        System.out.println("Login button clicked");
    }
}
