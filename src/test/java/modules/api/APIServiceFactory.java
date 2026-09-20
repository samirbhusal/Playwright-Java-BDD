package modules.api;

import com.microsoft.playwright.APIRequestContext;
import services.UserAuth;

public class APIServiceFactory {
    private UserAuth userAuth;

    private APIRequestContext request;

    public APIServiceFactory(APIRequestContext request) {
        this.request = request;
    }

    public UserAuth userAuth() {
        if (userAuth == null) {
            userAuth = new UserAuth(request);
        }
        return userAuth;
    }
}
