package screens;

import device.Device;
import org.openqa.selenium.By;
import screens.selectors.CountryCardSelectors;

public class CountryCard extends Screen {
    private final CountryCardSelectors selectors;

    public CountryCard(Device device) {
        super(device);
        selectors = new CountryCardSelectors(device.deviceType);
    }

    public void goBack() {
        device.driver.findElement(selectors.backButton()).click();
    }

    public By headerSelector(String countryName) {
            return switch (device.deviceType) {
                case IOS, ANDROID -> selectors.cardHeader(countryName);
            };
    }

    public boolean headerPresent(String countryName) {
        // Suboptimal - using static class members mixed with accessors for Android
        return switch (device.deviceType) {
            case IOS -> device.driver.findElement(selectors.cardHeader(countryName)).isDisplayed();
            case ANDROID ->
                device.driver.findElement(CountryCardSelectors.androidSelectors.get("cardHeader")).isDisplayed() &&
                        device.driver.findElement(selectors.cardHeader(countryName)).isDisplayed();

        };
    }
}
