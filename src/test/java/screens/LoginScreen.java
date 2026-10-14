package screens;

import core.ConfigLoader;
import exceptions.FrameworkException;
import helper.PlatformType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginScreen {
    private AppiumDriver driver;

    public LoginScreen(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(xpath = "//android.widget.ScrollView[@content-desc='test-Login']/android.view.ViewGroup/android.widget.ImageView[1]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name='assets/src/img/swag-labs-logo.png']")
    private WebElement loginScreenLogo;

    @AndroidFindBy(id = "android:id/button2")
    private WebElement androidAlertOkButton;

    @iOSXCUITFindBy(accessibility = "test-standard_user")
    @AndroidFindBy(accessibility = "test-standard_user")
    private WebElement standardUserAutoFill;

    @AndroidFindBy(accessibility = "test-LOGIN")
    @iOSXCUITFindBy(accessibility = "test-LOGIN")
    private WebElement loginButton;

    @AndroidFindBy(accessibility = "test-Username")
    @iOSXCUITFindBy(accessibility = "test-Username")
    private WebElement usernameField;

    @AndroidFindBy(accessibility = "test-Password")
    @iOSXCUITFindBy(accessibility = "test-Password")
    private WebElement passwordField;


    public void launch() {
        try {
            driver.manage().timeouts().getImplicitWaitTimeout();
            if (ConfigLoader.getPlatformType() == PlatformType.ANDROID) {
                androidAlertOkButton.click();
            }
            Assert.assertTrue(loginScreenLogo.isDisplayed());
        } catch (Exception e) {
            throw new FrameworkException("Error while launching Mobile driver: " + ConfigLoader.getPlatformType(), e);
        }
    }

    public void enterUsername(String userName) {
        try {
            usernameField.click();
            usernameField.clear();
            usernameField.sendKeys(userName);
        } catch (Exception e) {
            throw new FrameworkException("Error while entering user name: " + userName, e);
        }
    }

    public void enterPassword(String password) {
        try {
            passwordField.click();
            passwordField.clear();
            passwordField.sendKeys(password);
        } catch (Exception e) {
            throw new FrameworkException("Error while entering password: \n", e);
        }
    }

    public void clickLoginButton() {
        try {
            Assert.assertTrue(loginButton.isDisplayed());
            loginButton.click();
        } catch (Exception e) {
            throw new FrameworkException("Error while clicking login button: \n", e);
        }
    }
}
