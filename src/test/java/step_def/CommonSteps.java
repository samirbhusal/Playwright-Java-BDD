package step_def;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class CommonSteps {
    @Given("user launches the web app")
    public void launchWebApp() {
    }

    @When("user clicks the login button")
    public void clickLoginButton() {
        System.out.println("Login button clicked");
    }
}
