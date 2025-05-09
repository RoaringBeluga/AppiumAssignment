package screens;

import device.Device;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import screens.selectors.CountryListSelectors;

public class CountryList extends Screen {

    private final CountryListSelectors selectors;

    public CountryList(Device device) {
        super(device);
        this.selectors = new CountryListSelectors(device.deviceType);
    }

    public WebElement findCountryByName(String countryName) {
        return switch (device.deviceType) {
            case IOS -> this.device.driver.findElement(AppiumBy.name(countryName));
            case ANDROID -> this.device.driver.findElement(selectors.countryByName(countryName));
        };
    }

    public boolean isLoaded() {
        var header = device.driver.findElement(selectors.listHeader());
        return header.isDisplayed();
    }

    public void showCountry(String country) {
        scrollToText(country);
        var card = device.driver.findElement(selectors.countryByName(country));
        card.click();
    }

    public boolean countryIsVisible(String country) {
        return findCountryByName(country).isDisplayed();
    }

    public Integer elementCount() {
        return device.driver.findElements(selectors.countryCell()).size();
    }

    public WebElement listHeader() {
        return device.driver.findElement(selectors.listHeader());
    }

}
