package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import modules.AbstractStepDefinitions;
import org.testng.Assert;

public class LoginPage extends AbstractStepDefinitions {
    private final Page page;
    private final Locator loginButton;
    private final Locator loginEmailField;
    private final Locator loginPasswordField;
    private final Locator loginToYourAccountHeading;

    public LoginPage() {
        this.page = getPage();
        this.loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        this.loginEmailField = page.getByPlaceholder("Email Address").first();
        this.loginPasswordField = page.getByPlaceholder("Password");
        this.loginToYourAccountHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Login to your account"));
    }

    public void verifyLoginPage() {
        Assert.assertTrue(page.url().contains("login"));
        Assert.assertTrue(loginToYourAccountHeading.isVisible());
    }

    public void enterEmail(String email) {
        loginEmailField.clear();
        loginEmailField.type(email);
    }

    public void enterPassword(String password) {
        loginPasswordField.clear();
        loginPasswordField.type(password);
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public void clickLoginButton() {
        loginButton.click();
    }


}
