package services;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UserAuth {

    private final APIRequestContext request;

    public UserAuth(APIRequestContext request) {
        this.request = request;
    }

    public void launch() {
        assertThat(request.get("/")).isOK();
    }

    public APIResponse sendPostRequest(String endPoint, Map<String, String> fields) {
        return request.post(endPoint, RequestOptions.create().setData(fields));
    }
}
