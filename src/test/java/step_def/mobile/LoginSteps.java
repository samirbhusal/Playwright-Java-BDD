package step_def.mobile;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import modules.mobile.AbstractMobileStepDefinitions;

public class LoginSteps extends AbstractMobileStepDefinitions {

    @Given("user enters the username as {string}")
    public void enterUserName(String username) {
        screenFactory().getLoginScreen().enterUsername(username);
    }

    @And("user enters the password as {string}")
    public void enterPassword(String password) {
        screenFactory().getLoginScreen().enterPassword(password);
    }

    @When("user taps the login button")
    public void tapsLoginButton() {
        screenFactory().getLoginScreen().clickLoginButton();
    }

}
