package screens.selectors;

import device.DeviceType;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.util.Map;

public class CountryCardSelectors {
    private final DeviceType deviceType;
    public CountryCardSelectors(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
    public static Map<String, By> iOsSelectors = Map.of(
            "cardHeader",AppiumBy.xpath("//XCUIElementTypeStaticText[@name='Afghanistan']"),
            "backButton", AppiumBy.xpath("//XCUIElementTypeButton[@name='Countries']"),
            "countryName", AppiumBy.xpath("//XCUIElementTypeStaticText[@name='Afghanistan, AS']")
    );

    public static Map<String, By> androidSelectors = Map.of(
            "cardHeader", AppiumBy.xpath("//android.widget.TextView[@text='Country Details']"),
            "backButton", AppiumBy.xpath("//android.view.ViewGroup[@resource-id='com.example.wallmartexample:id/action_bar']/android.widget.ImageButton"),
            "countryName", AppiumBy.xpath("//XCUIElementTypeStaticText[@name='Afghanistan, AS']")
    );

    public By cardHeader(String country) {
        // Although it's not a header when it comes to Android, but
        // it works to indicate that we're on the card details screen
        return switch (deviceType) {
            case IOS -> AppiumBy.xpath("//XCUIElementTypeStaticText[@name='%s']".formatted(country));
            case ANDROID -> AppiumBy.xpath(
                    "//android.widget.TextView[@resource-id='com.example.wallmartexample:id/name_with_region_view' and @text='%s']"
                            .formatted(country)
            );
        };
    }

    public  By backButton() {
        return switch (deviceType) {
            case IOS -> AppiumBy.xpath("//XCUIElementTypeButton[@name='Countries']");
            case ANDROID -> AppiumBy.xpath("//android.view.ViewGroup[@resource-id='com.example.wallmartexample:id/action_bar']/android.widget.ImageButton");
        };
    }
}
