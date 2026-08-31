package modules;

public abstract class AbstractStepDefinitions {
    protected WebPlatform webPlatform;

    protected AbstractStepDefinitions() {
        this.webPlatform = new WebPlatform();
    }
}
