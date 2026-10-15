package modules.mobile;

import io.appium.java_client.AppiumDriver;
import screens.HomeScreen;
import screens.LoginScreen;

public class MobilePageFactory {
    private AppiumDriver driver;

    private LoginScreen loginScreen;
    private HomeScreen homeScreen;

    public MobilePageFactory(AppiumDriver driver) {
        this.driver = driver;
    }

    public LoginScreen getLoginScreen() {
        if (loginScreen == null) {
            loginScreen = new LoginScreen(driver);
        }
        return loginScreen;
    }

    public HomeScreen getHomeScreen() {
        if (homeScreen == null) {
            homeScreen = new HomeScreen(driver);
        }
        return homeScreen;
    }
}
