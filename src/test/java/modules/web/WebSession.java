package modules.web;

import core.PlaywrightDriverManager;
import interfaces.PlatformSession;

public class WebSession implements PlatformSession {

    @Override
    public void start() {
        PlaywrightDriverManager.initBrowserContextAndPage();
    }

    @Override
    public void stop() {
        PlaywrightDriverManager.closeContextAndPage();
        PlaywrightDriverManager.closeBrowser();
        PlaywrightDriverManager.closePlaywright();
    }
}
