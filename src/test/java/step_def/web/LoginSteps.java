package step_def.web;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import modules.AbstractWebStepDefinitions;


public class LoginSteps extends AbstractWebStepDefinitions {

    public LoginSteps() {
        super();
    }

    @And("user should be navigated to login page")
    public void verifyLoginPage() {
        pageFactory.loginPage().verifyLoginPage();
    }

    @And("user enters the valid email as {string}")
    public void enterEmail(String email) {
        pageFactory.loginPage().enterEmail(email);
    }

    @And("user enters the valid password as {string}")
    public void enterPassword(String password) {
        pageFactory.loginPage().enterPassword(password);
    }

    @When("user clicks the login button")
    public void clickLoginButton() {
        pageFactory.loginPage().clickLoginButton();
    }

}
