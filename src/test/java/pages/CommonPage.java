package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.ConfigLoader;
import modules.AbstractStepDefinitions;
import org.testng.Assert;

public class CommonPage extends AbstractStepDefinitions {
    private Page page;

    public CommonPage() {
        this.page = getPage();
    }


    public void launch() {
        page.waitForURL(ConfigLoader.getBaseUrl());
    }

    public void clickButton(String buttonName) {
        Locator locator = switch (buttonName) {
            case "Signup / Login" -> page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(buttonName));
            default -> throw new RuntimeException("Button " + buttonName + " not found");
        };
        Assert.assertTrue(locator.isVisible());
        locator.click();
    }

    public void verifyLandingPage() {
    }

    public void verifyUrl(String url) {
        Assert.assertEquals(getPage().url(), url);
    }

}
