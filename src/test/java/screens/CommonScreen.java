package screens;

import core.ConfigLoader;
import exceptions.FrameworkException;
import io.appium.java_client.AppiumDriver;

public class CommonScreen {
    private AppiumDriver driver;

    public CommonScreen(AppiumDriver driver) {
        this.driver = driver;
    }

    public void launch() {
        try {
            driver.wait(10000);
//            driver.findElement()
        } catch (Exception e) {
            throw new FrameworkException("Error while launching Mobile driver " + ConfigLoader.getPlatformType(), e);
        }

    }
}
