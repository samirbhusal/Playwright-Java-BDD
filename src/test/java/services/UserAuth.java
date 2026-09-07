package services;

import com.microsoft.playwright.APIRequestContext;
import core.PlaywrightDriverManager;
import interfaces.CommonActions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UserAuth implements CommonActions {

    private final APIRequestContext request;

    public UserAuth() {
        this.request = PlaywrightDriverManager.getRequest();
    }

    @Override
    public void launch() {
        System.out.println("API environment is set up");
        assertThat(request.get("https://reqres.in")).isOK();
    }
}
