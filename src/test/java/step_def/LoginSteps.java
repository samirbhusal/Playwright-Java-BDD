package step_def;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import modules.AbstractStepDefinitions;
import pages.LoginPage;


public class LoginSteps extends AbstractStepDefinitions {

    LoginPage loginPage = new LoginPage();

    @And("user should be navigated to login page")
    public void verifyLoginPage() {
        loginPage.verifyLoginPage();
    }

    @And("user enters the valid email as {string}")
    public void enterEmail(String email) {
        loginPage.enterEmail(email);
    }

    @And("user enters the valid password as {string}")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("user clicks the login button")
    public void clickLoginButton() {
        loginPage.clickLoginButton();
    }

}
