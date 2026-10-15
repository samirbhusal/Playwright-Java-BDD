package modules.mobile;

import core.MobileDriverManager;
import exceptions.FrameworkException;
import interfaces.PlatformSession;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class IOS_Session implements PlatformSession {
    @Override
    public void start() {
        try {
            XCUITestOptions options = new XCUITestOptions()
                    .setAutomationName("XCUITest")
                    .setDeviceName("iPhone 17 Pro")
                    .setUdid("147ED534-ECD5-492A-8EC1-DE06965F0F43")
                    .setPlatformVersion("26.0.1")
                    .setApp("/Users/samirbhusal/Documents/Development/mobilewright/ios/iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.1.zip")
                    .setWdaLaunchTimeout(Duration.ofSeconds(20));
            URL serverUrl = URI.create("http://127.0.0.1:4723/").toURL();
            AppiumDriver driver = new IOSDriver(serverUrl, options);
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
