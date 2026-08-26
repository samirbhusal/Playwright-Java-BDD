package modules;

import interfaces.Web_Platform;

public abstract class AbstractStepDefinitions {
    protected Web_Platform webPlatform;

    protected AbstractStepDefinitions() {
        webPlatform = new WebPlatform();
    }
}
