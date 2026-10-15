package core;

import exceptions.FrameworkException;
import io.appium.java_client.AppiumDriver;

public final class MobileDriverManager {
    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<AppiumDriver>();

    public static AppiumDriver getDriver() {
        return DRIVER.get();
    }

    public static void setDriver(AppiumDriver driver) {
        DRIVER.set(driver);
    }

    public static void quitDriver() {
        try {
            DRIVER.get().quit();
        } catch (Exception e) {
            throw new FrameworkException("Error while quitting AppiumDriver", e);
        } finally {
            DRIVER.remove();
        }
    }
}
