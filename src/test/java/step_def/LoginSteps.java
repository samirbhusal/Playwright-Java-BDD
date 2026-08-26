package step_def;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;


public class LoginSteps {
    @And("user enters the valid email as {string}")
    public void enterEmail(String email) {
        System.out.println(email);
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
