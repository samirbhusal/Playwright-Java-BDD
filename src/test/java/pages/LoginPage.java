package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    private final Page page;
    private final Locator loginButton;

    public LoginPage(Page page) {
        this.page = page;
        this.loginButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login"));
    }

    public Locator getLoginButton() {
        return loginButton;
    }

}
