package core;

import com.microsoft.playwright.*;

import java.util.HashMap;
import java.util.Map;

public class PlaywrightDriverManager extends BrowserFactory {
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();

    private static ThreadLocal<APIRequestContext> request = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return playwright.get();
    }

    public static Browser getBrowser() {
        return browser.get();
    }

    public static BrowserContext getBrowserContext() {
        return browserContext.get();
    }

    public static Page getPage() {
        return page.get();
    }

    public static APIRequestContext getRequest() {
        return request.get();
    }

    /**
     * Initialize Playwright browser and navigate to base URL
     * Should be called in @BeforeMethod or @BeforeSuite
     */
    public static void initPlaywright() {
        // Playwright setup
        try {
            playwright.set(Playwright.create());
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while initializing playwright browser: " + e);
        }
    }

    public static void intiBrowserContextAndPage() {
        browser.set(getBrowser(getPlaywright()));
        browserContext.set(getBrowser().newContext());
        page.set(getBrowserContext().newPage());
    }

    public static void initAPIRequestContext() {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Accept", "application/json");
            request.set(getPlaywright().request().newContext(new APIRequest.NewContextOptions()
                    .setBaseURL("https://reqres.in")
                    .setExtraHTTPHeaders(headers)
            ));
        } catch (Exception e) {
            throw new RuntimeException("Error while initializing playwright request context" + e.getStackTrace());
        }
    }

    /**
     * Close the current page
     * Should be called in @AfterMethod or @AfterSuite
     */
    public static void closePage() {
        if (page.get() != null) {
            page.get().close();
            page.remove();
        }
    }

    /**
     * Close the browser context
     */
    public static void closeBrowserContext() {
        if (browserContext.get() != null) {
            browserContext.get().close();
            browserContext.remove();
        }
    }

    /**
     * Close the browser instance
     */
    public static void closeBrowser() {
        if (browser.get() != null) {
            browser.get().close();
            browser.remove();
        }
    }

    /**
     * Close Playwright instance
     */
    public static void closePlaywright() {
        if (playwright.get() != null) {
            playwright.get().close();
            playwright.remove();
        }
    }


    /**
     * Cleanup - closes  Playwright and browser resources in correct order
     * Should be called in @AfterMethod or @AfterSuite
     */
    public static void closePlaywrightInstance() {
        closePlaywright();
    }

    public static void closeContextAndPage() {
        closePage();
        closeBrowserContext();
        closeBrowser();
    }
}
