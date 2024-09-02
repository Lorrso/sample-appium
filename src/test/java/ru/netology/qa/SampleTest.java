package ru.netology.qa;

import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_ACTIVITY;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.MobileCapabilityType.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

@TestInstance(Lifecycle.PER_CLASS)
public class SampleTest {

    AndroidDriver driver;

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

        driver = new AndroidDriver(this.getUrl(), desiredCapabilities);
    }

    @Test
    public void testChangeToNullText() {
        var textView = driver.findElement(id("ru.netology.testing.uiautomator:id/textToBeChanged"));
        var userInput = driver.findElement(id("ru.netology.testing.uiautomator:id/userInput"));
        var buttonChange = driver.findElement(id("ru.netology.testing.uiautomator:id/buttonChange"));
        String textToSet = "    ";

        textView.isDisplayed();
        String startingText = textView.getText();
        userInput.isDisplayed();
        userInput.sendKeys(textToSet);
        buttonChange.isDisplayed();
        buttonChange.click();

        String result = textView.getText();
        Assertions.assertEquals(result, startingText);
    }

    @Test
    public void testViewTextInNewActivity() {
        var userInput = driver.findElement(id("ru.netology.testing.uiautomator:id/userInput"));
        var buttonChange = driver.findElement(id("ru.netology.testing.uiautomator:id/buttonChange"));
        var buttonActivity = driver.findElement(id("ru.netology.testing.uiautomator:id/buttonActivity"));
        String textToSet = "Netology";

        userInput.isDisplayed();
        userInput.sendKeys(textToSet);
        buttonChange.isDisplayed();
        buttonChange.click();
        buttonActivity.isDisplayed();
        buttonActivity.click();

        String result = driver.findElement(id("ru.netology.testing.uiautomator:id/text")).getText();
        Assertions.assertEquals(result, textToSet);
    }

    @AfterAll
    public void quitDriver() {
        driver.quit();
    }
}
