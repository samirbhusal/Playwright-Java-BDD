package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.ConfigLoader;
import core.PlaywrightDriverManager;
import exceptions.FrameworkException;
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
        try {
            page.navigate(ConfigLoader.getBaseUrl());
            page.waitForURL(ConfigLoader.getBaseUrl());
        } catch (Exception e) {
            throw new FrameworkException("Error while launching page " + ConfigLoader.getBaseUrl(), e);
        }

    }

    public void clickButton(String buttonName) {
        try {
            Locator locator = switch (buttonName) {
                case "Signup / Login" -> page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(buttonName));
                default -> throw new RuntimeException("Button - " + buttonName + " not found");
            };
            assertThat(locator).isVisible();
            locator.click();
        } catch (Exception e) {
            throw new FrameworkException("Error while clicking button: " + buttonName, e);
        }
    }

    public void verifyLandingPage() {
    }

    public void verifyUrl(String url) {
        try {
            assertThat(page).hasURL(Pattern.compile(url));
        } catch (Exception e) {
            throw new FrameworkException("Error while verifying url: " + url, e);
        }
    }

}
