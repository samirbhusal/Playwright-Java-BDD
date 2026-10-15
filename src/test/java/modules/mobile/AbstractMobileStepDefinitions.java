package modules.mobile;

import core.MobileDriverManager;
import exceptions.FrameworkException;
import io.appium.java_client.AppiumDriver;

public abstract class AbstractMobileStepDefinitions {
    private MobilePageFactory mobilePageFactory;

    protected MobilePageFactory screenFactory() {
        if (mobilePageFactory == null) {
            AppiumDriver driver = MobileDriverManager.getDriver();
            if (driver == null) {
                throw new FrameworkException("No Playwright page is available for this scenario");
            }
            mobilePageFactory = new MobilePageFactory(driver);
        }
        return mobilePageFactory;
    }
}
