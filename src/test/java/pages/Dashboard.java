package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import exceptions.FrameworkException;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static helper.DateHelper.departureDateFormat;

public class Dashboard {
    private Page page = null;
    private Locator dashBoardTitle;
    private Locator departureField;

    public Dashboard(Page page) {
        this.page = page;
        departureField = page.getByLabel("Where from?", new Page.GetByLabelOptions().setExact(true));
    }

    public void dashboardTitle(String title) {
        try {
            Assert.assertEquals(title, dashBoardTitle.getAttribute("alt"));
        } catch (Exception e) {
            throw new FrameworkException("Error while getting dashboard title", e);
        }
    }

    public void validateTheTitleDisplayedAs(String title) {
        try {
            dashBoardTitle = page.getByRole(AriaRole.HEADING).filter(new Locator.FilterOptions().setHasText(title)).first();
            assertThat(dashBoardTitle).isVisible();
        } catch (Exception e) {
            throw new FrameworkException("Error while validating the title displayed as: " + title, e);
        }

    }

    public void validateDepartureFieldText(String text) {
        try {
            assertThat(departureField).hasText(text);
        } catch (Exception e) {
            throw new FrameworkException("Error while validating the departure field text", e);
        }

    }

    public void bookCheapestFlightDate() {
        try {
            Locator ticketType = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Change ticket type."));
            Locator oneWayTicketOption = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("One way"));

            Locator passengerDropdownBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("passenger"));
            Locator ticketClass = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Economy (include Basic)"));

            Locator whereTo = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Where to?"));

            Locator departureInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Departure"));

            // Finds the button inside the grid cell that contains the number 21
            String departureDate = departureDateFormat();
            Locator dayButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(departureDate));

            Locator doneButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Done"));
            Locator exploreButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Explore")).first();
            Locator gotItButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Got it")).first();


            ticketType.click();
            oneWayTicketOption.click();

            whereTo.click();
            whereTo.fill("Dallas, Texas");
            whereTo.press("Enter");

            departureInput.click();
            dayButton.click();
            doneButton.click();
            exploreButton.click();
            gotItButton.click();
            page.waitForTimeout(10000);
        } catch (Exception e) {
            throw new FrameworkException("Error while booking cheapest ticket", e);
        }


    }

    public void isElementVisible(Locator locator) {
        assertThat(locator).isVisible();
    }
}
