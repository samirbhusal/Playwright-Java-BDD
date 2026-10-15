package modules.mobile;

import core.MobileDriverManager;
import exceptions.FrameworkException;
import interfaces.PlatformSession;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;

public class AndroidSession implements PlatformSession {
    @Override
    public void start() {
        try {
            UiAutomator2Options androidOptions = new UiAutomator2Options()
                    .setDeviceName("Pixel_10_Pro")
                    .setAvd("Pixel_10_Pro") //setAvd launches even if emulator is not opened
                    .setPlatformVersion("17")
                    .setApp("/Users/samirbhusal/Documents/Development/mobilewright/apks/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk")
                    .setAppWaitActivity("*");
            AppiumDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), androidOptions);
            MobileDriverManager.setDriver(driver);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FrameworkException("Could not start Android session", e);
        }
    }

    @Override
    public void stop() {
        MobileDriverManager.quitDriver();
    }
}
