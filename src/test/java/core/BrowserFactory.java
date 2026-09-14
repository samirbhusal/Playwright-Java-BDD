package core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

import java.util.List;

public class BrowserFactory {

    protected static Browser getRandomSelectedBrowser(Playwright playwright) {
        LaunchOptions options = new LaunchOptions()
//                .setSlowMo(500)
                .setArgs(List.of("--start-maximized"))
                .setHeadless(ConfigLoader.headless());


        BrowserChoice browserName = BrowserChoice.random();

        return getSelectedBrowser(playwright, browserName, options);
    }

    private static Browser getSelectedBrowser(Playwright playwright, BrowserChoice name, LaunchOptions options) {
        switch (name) {
            case FIREFOX:
                return playwright.firefox().launch(options);
            case WEBKIT:
                return playwright.webkit().launch(options);
            case CHROME:
            default:
                return playwright.chromium().launch(options.setChannel("chrome"));
        }
    }


}
