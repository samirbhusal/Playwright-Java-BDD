package modules;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import core.PlaywrightDriverManager;
import interfaces.WebActions;
import interfaces.WebValidations;
import org.testng.Assert;
import pages.Dashboard;
import pages.LoginPage;

public class WebPlatform implements WebValidations, WebActions {
    private LoginPage loginPage;
    private Dashboard dashboard;

    public Page getPage() {
        Page page = PlaywrightDriverManager.getPage();
        if (page == null) {
            throw new IllegalStateException("Browser not initialized! step_def.Hooks.java should have run before this.");
        }
        return page;
    }


    private LoginPage loginPage() {
        if (loginPage == null) loginPage = new LoginPage(getPage());
        return loginPage;
    }

    private Dashboard dashboard() {
        if (dashboard == null) dashboard = new Dashboard(getPage());
        return dashboard;
    }

    @Override
    public void launch() {
        getPage().waitForLoadState();
    }

    @Override
    public void clickButton(String buttonName) {
        Locator locator = switch (buttonName) {
            case "Login" -> loginPage().getLoginButton();
            default -> throw new IllegalArgumentException("Invalid button name: " + buttonName);
        };
        locator.click();
    }

    @Override
    public void verifyLandingPage() {
        dashboard().dashboardTitle("Website for automation practice");
    }

    @Override
    public void verifyUrl(String url) {
        Assert.assertEquals(getPage().url(), url);
    }
}
