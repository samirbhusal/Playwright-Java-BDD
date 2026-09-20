package modules.api;

import com.microsoft.playwright.APIRequestContext;
import core.PlaywrightDriverManager;
import exceptions.FrameworkException;

public class AbstractApiStepDefinition {
    private APIServiceFactory apiServiceFactory;

    public APIServiceFactory apiServices() {
        if (apiServiceFactory == null) {
            APIRequestContext request = PlaywrightDriverManager.getRequest();

            if (request == null) {
                throw new FrameworkException("No APIRequestContext is available for this scenario");
            }
            apiServiceFactory = new APIServiceFactory(request);
        }
        return apiServiceFactory;
    }


}
