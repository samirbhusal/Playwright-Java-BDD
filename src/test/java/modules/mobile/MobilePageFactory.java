package modules.mobile;

import io.appium.java_client.AppiumDriver;
import screens.CommonScreen;

public class MobilePageFactory {
    private AppiumDriver driver;

    private CommonScreen commonScreen;

    public MobilePageFactory(AppiumDriver driver) {
        this.driver = driver;
    }

    public CommonScreen getCommonScreen() {
        if (commonScreen == null) {
            commonScreen = new CommonScreen(driver);
        }
        return commonScreen;
    }
}
