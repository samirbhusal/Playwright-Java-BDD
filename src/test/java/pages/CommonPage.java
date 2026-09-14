package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.ConfigLoader;
import core.PlaywrightDriverManager;
import interfaces.CommonActions;
import modules.AbstractStepDefinitions;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CommonPage extends AbstractStepDefinitions implements CommonActions {
    private Page page;

    public CommonPage() {
        this.page = PlaywrightDriverManager.getPage();
    }

    @Override
    public void launch() {
        page.navigate(ConfigLoader.getBaseUrl());
        page.waitForURL(ConfigLoader.getBaseUrl());
    }

    public void clickButton(String buttonName) {
        Locator locator = switch (buttonName) {
            case "Signup / Login" -> page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(buttonName));
            default -> throw new RuntimeException("Button - " + buttonName + " not found");
        };
        assertThat(locator).isVisible();
        locator.click();
    }

    public void verifyLandingPage() {
    }

    public void verifyUrl(String url) {
        assertThat(page).hasURL(Pattern.compile(url));
    }

}
