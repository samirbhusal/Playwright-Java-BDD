package step_def;

import core.ConfigLoader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.CommonPage;

public class CommonSteps {

    CommonPage commonPage = new CommonPage();

    @Given("user launches the web app")
    public void launchWebApp() {
        commonPage.launch();
    }

    @When("user verifies the landing page")
    public void verifyLandingPage() {
        commonPage.verifyUrl(ConfigLoader.getBaseUrl() + "/");
        commonPage.verifyLandingPage();
    }

    @Given("user clicks the {string} button")
    public void clickTheButton(String button) {
        commonPage.clickButton(button);
    }
}
