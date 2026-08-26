package core;

import com.microsoft.playwright.Playwright;

public class PlaywrightDriverManager {

    private static final ThreadLocal<Playwright> tlPlaywright = ThreadLocal.withInitial(Playwright::create);
//    private static final ThreadLocal<Browser> browser = ThreadLocal.withInitial(Browser::browserType)
}
