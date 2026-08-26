package modules;

import com.microsoft.playwright.Page;
import core.PlaywrightDriverManager;
import interfaces.Web_Platform;

public class WebPlatform implements Web_Platform {

    private Page page;

    @Override
    public void launch() {
        page = PlaywrightDriverManager.getPage();
        if (page == null) {
            throw new IllegalStateException("Browser not initialized! step_def.Hooks.java should have run before this.");
        }
        page.waitForLoadState();
    }
}
