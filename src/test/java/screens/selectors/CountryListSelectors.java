package screens.selectors;

import device.DeviceType;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.util.Map;

public class CountryListSelectors {
    private final DeviceType deviceType;
    public CountryListSelectors(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
    public static Map<String, By> iOsSelectors = Map.of(
            "header", AppiumBy.xpath("//XCUIElementTypeNavigationBar[@name='Countries']"),
            "cell", AppiumBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[1]"),
            "countryName", AppiumBy.xpath("//XCUIElementTypeStaticText[@name='Afghanistan, AS']"),
            "searchBar", AppiumBy.xpath("//XCUIElementTypeSearchField[@name='Search']")
    );
    public static Map<String, By> androidSelectors = Map.of(
            "header", AppiumBy.xpath("//android.view.ViewGroup[@resource-id='com.example.wallmartexample:id/action_bar']"),
            "cell", AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.example.wallmartexample:id/country_recycler_view']/android.view.ViewGroup[1]"),
            "countryName", AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.example.wallmartexample:id/name_with_region_view\" and @text=\"Afghanistan, AS\"]"),
            "searchButton", AppiumBy.xpath("//android.widget.Button[@content-desc='Search']"),
            "searchBar", AppiumBy.xpath("//android.widget.AutoCompleteTextView[@resource-id='com.example.wallmartexample:id/search_src_text']")
    );

    public By listHeader() {
        return switch (deviceType) {
            case IOS -> AppiumBy.xpath("//XCUIElementTypeNavigationBar[@name='Countries']");
            case ANDROID -> AppiumBy.xpath("//android.view.ViewGroup[@resource-id='com.example.wallmartexample:id/action_bar']");

        };
    }
    public By countryByName(String country) {
        return switch (deviceType) {
            case IOS -> AppiumBy.xpath("//XCUIElementTypeStaticText[@name='%s']".formatted(country));
            case ANDROID -> AppiumBy
                    .xpath(
                            "//android.widget.TextView[@resource-id='com.example.wallmartexample:id/name_with_region_view' and @text='%s']"
                    .formatted(country));
        };
    }

    public By searchField() {
        return switch (deviceType) {
            case IOS -> AppiumBy.xpath("//XCUIElementTypeSearchField[@name='Search']");
            case ANDROID -> AppiumBy.xpath("//android.widget.AutoCompleteTextView[@resource-id='com.example.wallmartexample:id/search_src_text']");
        };
    }

    public By searchButton() {
        return AppiumBy.xpath("//android.widget.Button[@content-desc='Search']");
    }

    public By cancelSearchButton() {
        return switch (deviceType) {
            case IOS -> AppiumBy.xpath("//XCUIElementTypeStaticText[@name=\"Cancel\"]");
            case ANDROID -> AppiumBy.xpath("//android.widget.ImageView[@content-desc=\"Clear query\"]");
        };
    }

    public By collapseButton() {
        return AppiumBy.xpath("//android.widget.ImageButton[@content-desc=\"Collapse\"]");
    }

    public By countryCell() {
        return switch (deviceType) {
            case IOS -> AppiumBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[1]");
            case ANDROID -> AppiumBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[1]");
        };
    }
}
