package core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightDriverManager extends BrowserFactory {
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();

    protected PlaywrightDriverManager() {
        // static-only utility, no instances
    }

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

    /**
     * Initialize Playwright browser and navigate to base URL
     * Should be called in @BeforeMethod or @BeforeSuite
     */
    public static void initBrowser() {
        // Playwright setup
        playwright.set(Playwright.create());
        browser.set(getRandomBrowser(getPlaywright()));
        browserContext.set(getBrowser().newContext());
        page.set(getBrowserContext().newPage());
        getPage().navigate(ConfigLoader.getBaseUrl());
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
     * Complete cleanup - closes all Playwright resources in correct order
     * Should be called in @AfterMethod or @AfterSuite
     */
    public static void closeBrowserInstance() {
        closePage();
        closeBrowserContext();
        closeBrowser();
        closePlaywright();
    }
}
