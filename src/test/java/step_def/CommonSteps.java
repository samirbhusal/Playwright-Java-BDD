package step_def;

import core.ConfigLoader;
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

    @When("user verifies the landing page")
    public void verifyLandingPage() {
        webPlatform.verifyUrl(ConfigLoader.getBaseUrl() + "/");
        webPlatform.verifyLandingPage();
    }

    @When("user clicks the login button")
    public void clickLoginButton() {
        System.out.println("Login button clicked");
    }
}
