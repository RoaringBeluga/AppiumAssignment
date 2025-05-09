package screens;

import device.Device;
import screens.selectors.CountryListSelectors;

public class SearchField extends Screen {
    private CountryListSelectors selectors;
    public SearchField(Device device) {
        super(device);
        selectors = new CountryListSelectors(device.deviceType);
    }

    public void searchFor(String searchTerm) {
        var search = switch (device.deviceType) {
            case IOS -> findElement(selectors.searchField());
            case ANDROID -> {
                findElement(selectors.searchButton()).click();
                yield findElement(selectors.searchField());
            }
        };
        search.sendKeys(searchTerm);
    }
}
