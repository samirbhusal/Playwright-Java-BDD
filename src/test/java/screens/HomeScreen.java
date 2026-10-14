package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomeScreen {
    private AppiumDriver driver;

    public HomeScreen(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"PRODUCTS\"]")
    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"PRODUCTS\"]")
    private WebElement productsTitle;

    public void verifyHomeScreen() {
        Assert.assertTrue(productsTitle.isDisplayed());
        Assert.assertEquals(productsTitle.getText(), "PRODUCTS");
    }
}
