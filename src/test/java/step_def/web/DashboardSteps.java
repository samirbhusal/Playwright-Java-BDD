package step_def.web;

import io.cucumber.java.en.And;
import modules.web.AbstractWebStepDefinitions;

public class DashboardSteps extends AbstractWebStepDefinitions {

    @And("user validates the title displayed as {string}")
    public void validateTheTitleDisplayedAs(String title) {
        pageFactory().dashboard().validateTheTitleDisplayedAs(title);
    }

    @And("user validates the departure field has text as {string}")
    public void validateTheDepartureFieldHasText(String text) {
        pageFactory().dashboard().validateDepartureFieldText(text);
    }

    @And("user books a cheapest flight date")
    public void booksACheapestFlightDate() {
        pageFactory().dashboard().bookCheapestFlightDate();
    }

}
