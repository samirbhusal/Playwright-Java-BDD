package step_def;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import modules.AbstractStepDefinitions;


public class LoginSteps extends AbstractStepDefinitions {

    @And("user enters the valid email as {string}")
    public void enterEmail(String email) {
        webPlatform.getPage().waitForTimeout(10000);
        System.out.print(email);
    }

    @And("user enters the valid password as {string}")
    public void enterPassword(String password) {
        System.out.println(password);
    }

    @Then("user should land in dashboard")
    public void landInDashboard() {
        System.out.println("Land in dashboard");
    }

}
