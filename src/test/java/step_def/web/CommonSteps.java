package step_def.web;

import core.ConfigLoader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import modules.web.AbstractWebStepDefinitions;

public class CommonSteps extends AbstractWebStepDefinitions {

    public CommonSteps() {
        super();
    }

    @Given("user launches the web app")
    public void launchWebApp() {
        pageFactory().commonPage().launch();
    }

    @When("user verifies the landing page")
    public void verifyLandingPage() {
        pageFactory().commonPage().verifyUrl(ConfigLoader.getBaseUrl() + "/");
        pageFactory().commonPage().verifyLandingPage();
    }

    @Given("user clicks the {string} button")
    public void clickTheButton(String button) {
        pageFactory().commonPage().clickButton(button);
    }
}
