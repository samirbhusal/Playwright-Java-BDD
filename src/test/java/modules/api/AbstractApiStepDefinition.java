package modules.api;

import com.microsoft.playwright.APIRequestContext;
import core.PlaywrightDriverManager;
import exceptions.FrameworkException;

import java.util.HashMap;
import java.util.Map;

public class AbstractApiStepDefinition {
    private APIServiceFactory apiServiceFactory;
    private static final ThreadLocal<Map<String, Object>> SCENARIO_DATA =
            ThreadLocal.withInitial(HashMap::new);


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

    public static void save(String key, Object value) {
        SCENARIO_DATA.get().put(key, value);
    }

    public static <T> T get(String key, Class<T> type) {
        Object value = SCENARIO_DATA.get().get(key);
        if (value == null) {
            throw new FrameworkException("No scenario value found for key: " + key);
        }
        return type.cast(value);
    }

    public static void clearScenarioData() {
        SCENARIO_DATA.remove();
    }


}
