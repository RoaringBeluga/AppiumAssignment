package screens;

import com.google.common.collect.ImmutableMap;
import device.Device;
import device.DeviceType;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class Screen {
    final Device device;

    public Screen(Device device) {
        this.device = device;
    }

    public WebElement findElement(By locator) {
        return device.driver.findElement(locator);
    }

    public void scrollToText(String text) {
        switch (device.deviceType) {
            case DeviceType.ANDROID:
                device.driver.findElement(AppiumBy
                    .androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"%s\"));".formatted(text)));
                break;
            case IOS:
                device.driver.executeScript("mobile: scroll", Map.ofEntries(
                        Map.entry("predicateString", "name==\"%s\"".formatted(text))
                ));
                break;
        }
    }

    public void pullDown() {
        device.driver.executeScript("mobile:scroll", ImmutableMap.of("direction", "up"));
    }

}
