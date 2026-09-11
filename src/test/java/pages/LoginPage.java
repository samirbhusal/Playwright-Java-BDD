package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.PlaywrightDriverManager;
import modules.AbstractStepDefinitions;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class LoginPage extends AbstractStepDefinitions {
    private final Page page;
    private final Locator loginButton;
    private final Locator loginEmailField;
    private final Locator loginPasswordField;
    private final Locator loginToYourAccountHeading;

    public LoginPage() {
        this.page = PlaywrightDriverManager.getPage();
        this.loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        this.loginEmailField = page.getByPlaceholder("Email Address").first();
        this.loginPasswordField = page.getByPlaceholder("Password");
        this.loginToYourAccountHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Login to your account"));
    }

    public void verifyLoginPage() {
        assertThat(page).hasURL(Pattern.compile("login"));
        assertThat(loginToYourAccountHeading).isVisible();
    }

    public void enterEmail(String email) {
        assertThat(loginEmailField).isVisible();
        loginEmailField.clear();
        loginEmailField.type(email);
    }

    public void enterPassword(String password) {
        assertThat(loginPasswordField).isVisible();
        loginPasswordField.clear();
        loginPasswordField.type(password);
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public void clickLoginButton() {
        assertThat(loginButton).isVisible();
        loginButton.click();
    }


}
