package services;

import com.microsoft.playwright.APIRequestContext;
import core.ConfigLoader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UserAuth {

    private final APIRequestContext request;

    public UserAuth(APIRequestContext request) {
        this.request = request;
    }

    public void launch() {
        System.out.println("API environment is set up");
        assertThat(request.get(ConfigLoader.getBaseUrl())).isOK();
    }
}
