package core;

import com.microsoft.playwright.*;
import exceptions.FrameworkException;

import java.util.HashMap;
import java.util.Map;

import static core.BrowserFactory.getRandomSelectedBrowser;

public class PlaywrightDriverManager {
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


    public static void initPlaywright() {
        try {
            playwright.set(Playwright.create());
        } catch (Exception e) {
            throw new RuntimeException("Error while initializing Playwright: " + e.getMessage(), e);
        }
    }

    public static void initBrowser() {
        initPlaywright();
        try {
            browser.set(getRandomSelectedBrowser(getPlaywright()));
        } catch (Exception e) {
            throw new RuntimeException("Error while launching browser: " + e.getMessage(), e);
        }
    }

    public static void initBrowserContextAndPage() {
        initBrowser();
        try {
            browserContext.set(getBrowser().newContext());
            page.set(getBrowserContext().newPage());
        } catch (Exception e) {
            throw new RuntimeException("Error while creating browser context/page: " + e.getMessage(), e);
        }
    }

    public static void initAPIRequestContext() {
        initPlaywright();
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Accept", "application/json");
            request.set(getPlaywright().request().newContext(new APIRequest.NewContextOptions()
                    .setBaseURL("https://reqres.in")
                    .setExtraHTTPHeaders(headers)
            ));
        } catch (Exception e) {
            throw new RuntimeException("Error while initializing playwright request context: " + e.getMessage(), e);
        }
    }

    public static void closeAPIRequestContext() {
        if (request.get() != null) {
            try {
                request.get().dispose();
            } finally {
                request.remove();
            }
        }
    }

    /**
     * Close the current page
     * Should be called in @AfterMethod or @AfterSuite
     */
    public static void closePage() {
        if (page.get() != null) {
            try {
                page.get().close();
            } catch (Exception e) {
                throw new FrameworkException("Error while closing playwright page: " + e.getMessage(), e);
            } finally {
                page.remove();
            }
        }
    }

    /**
     * Close the browser context
     */
    public static void closeBrowserContext() {
        if (browserContext.get() != null) {
            try {
                browserContext.get().close();
            } catch (Exception e) {
                throw new FrameworkException("Error while closing playwright browser context: " + e.getMessage(), e);
            } finally {
                browserContext.remove();
            }
        }
    }

    /**
     * Close the browser instance
     */
    public static void closeBrowser() {
        if (browser.get() != null) {
            try {
                browser.get().close();
            } catch (Exception e) {
                throw new FrameworkException("Error while closing playwright browser: " + e.getMessage(), e);
            } finally {
                browser.remove();
            }
        }
    }

    /**
     * Close Playwright instance
     */
    public static void closePlaywright() {
        if (playwright.get() != null) {
            try {
                playwright.get().close();
            } catch (Exception e) {
                throw new FrameworkException("Error while closing Playwright: " + e.getMessage(), e);
            } finally {
                playwright.remove();
            }
        }
    }

    /**
     * Close this scenario's page and context.
     */
    public static void closeContextAndPage() {
        closePage();
        closeBrowserContext();
    }
}
