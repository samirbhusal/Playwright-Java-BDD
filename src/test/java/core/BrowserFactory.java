package core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

import java.util.List;

public class BrowserFactory {

    public static Browser getRandomBrowser(Playwright playwright) {
        LaunchOptions options = new LaunchOptions()
//                .setSlowMo(500)
                .setArgs(List.of("--start-maximized"))
                .setHeadless(ConfigLoader.headless());


        BrowserChoice browserName = BrowserChoice.random();

        return getChoosenBrowser(playwright, browserName, options);
    }

    // overloading
    public static Browser getRandomBrowser(Playwright playwright, BrowserChoice browser, boolean headless) {
        LaunchOptions options = new LaunchOptions().setHeadless(headless);
        return getChoosenBrowser(playwright, browser, options);
    }

    // custom common method to get Browser
    private static Browser getChoosenBrowser(Playwright playwright, BrowserChoice name, LaunchOptions options) {
        switch (name) {
            case FIREFOX:
                return playwright.firefox().launch(options);
            case WEBKIT:
                return playwright.webkit().launch(options);
//            case EDGE:
//                return playwright.chromium().launch(options.setChannel("edge"));
            case CHROME:
            default:
                return playwright.chromium().launch(options.setChannel("chrome"));
        }
    }


}
