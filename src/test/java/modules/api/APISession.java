package modules.api;

import core.PlaywrightDriverManager;
import interfaces.PlatformSession;

public class APISession implements PlatformSession {

    @Override
    public void start() {
        PlaywrightDriverManager.initAPIRequestContext();
    }

    @Override
    public void stop() {
        System.out.println("API Session stopped");
    }
}
