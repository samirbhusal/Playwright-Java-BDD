package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.PlaywrightDriverManager;
import exceptions.FrameworkException;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class LoginPage {
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
        try {
            assertThat(page).hasURL(Pattern.compile("login"));
            assertThat(loginToYourAccountHeading).isVisible();
        } catch (Exception e) {
            throw new FrameworkException("Error verifying Login page", e);
        }

    }

    public void enterEmail(String email) {
        try {
            assertThat(loginEmailField).isVisible();
            loginEmailField.clear();
            loginEmailField.type(email);
        } catch (Exception e) {
            throw new FrameworkException("Error verifying entering email in login page", e);
        }
    }

    public void enterPassword(String password) {
        try {
            assertThat(loginPasswordField).isVisible();
            loginPasswordField.clear();
            loginPasswordField.type(password);
        } catch (Exception e) {
            throw new FrameworkException("Error verifying entering password in login page", e);
        }
    }

    public void login(String email, String password) {
        try {
            enterEmail(email);
            enterPassword(password);
            clickLoginButton();
        } catch (Exception e) {
            throw new FrameworkException("Error while login", e);
        }
    }

    public void clickLoginButton() {
        try {
            assertThat(loginButton).isVisible();
            loginButton.click();
        } catch (Exception e) {
            throw new FrameworkException("Error while clicking login button", e);
        }
    }


}
