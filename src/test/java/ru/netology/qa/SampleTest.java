package ru.netology.qa;

import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import ru.netology.qa.screens.MainScreen;

import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_ACTIVITY;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.MobileCapabilityType.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

@TestInstance(Lifecycle.PER_CLASS)
public class SampleTest {

    private AppiumDriver driver;
    MainScreen mainScreen;

    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    public void setUp() {
        var desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PLATFORM_NAME, "android");
        desiredCapabilities.setCapability(DEVICE_NAME, "any name");
        desiredCapabilities.setCapability(APP_PACKAGE, "ru.netology.testing.uiautomator");
        desiredCapabilities.setCapability(APP_ACTIVITY, "ru.netology.testing.uiautomator.MainActivity");
        desiredCapabilities.setCapability(AUTOMATION_NAME, "uiautomator2");

        driver = new AppiumDriver(this.getUrl(), desiredCapabilities);
        mainScreen = new MainScreen(driver);
    }

    @Test
    public void testChangeToNullText() {
        var textToSet = "    ";

        String textView = mainScreen.textToBeChanged.getText();
        mainScreen.userInput.sendKeys(textToSet);
        mainScreen.buttonChange.click();

        String result = mainScreen.textToBeChanged.getText();
        Assertions.assertEquals(result, textView);
    }

    @Test
    public void testViewTextInNewActivity() {
        String textToSet = "Netology";

        mainScreen.userInput.sendKeys(textToSet);
        mainScreen.buttonChange.click();
        mainScreen.buttonActivity.click();

        var result = mainScreen.newActivityText.getText();
        Assertions.assertEquals(result, textToSet);
    }

    @AfterAll
    public void quitDriver() {
        driver.quit();
    }
}
