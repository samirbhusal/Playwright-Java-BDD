package core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {

    public static Browser getRandomBrowser(Playwright playwright) {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(ConfigLoader.headless());

        BrowserChoice browserName = BrowserChoice.random();

        switch (browserName) {
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

//    public static Browser getRandomBrowser() {
//        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(ConfigLoader.headless());
//        Playwright playwright = null;
//        return playwright.chromium().launch(options.setChannel("chrome"));
//        }
//    }


}
