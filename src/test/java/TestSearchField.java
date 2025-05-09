import device.Device;
import device.DeviceType;
import device.FileSettings;
import device.SettingsProvider;
import io.appium.java_client.appmanagement.ApplicationState;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import screens.CountryCard;
import screens.CountryList;
import screens.SearchField;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestSearchField {
    private final String countryName = "Brazil, SA";
    private final String partialCountryName = "Brazil";

    Device device;
    SettingsProvider settings = new FileSettings("config.yaml");
    CountryList countryList;
    SearchField searchField;
    CountryCard countryCard;

    Integer countryCount = 0;

    @AfterClass
    public void tearDown() {
        this.device.shutDown();
    }

    @Factory(
            dataProvider = "DeviceTypes",
            dataProviderClass = DeviceTypeProvider.class
    )
    public TestSearchField(DeviceType deviceType) {
        this.settings.setDeviceType(deviceType);
        this.device = new Device(this.settings);
        countryList = new CountryList(device);
        searchField = new SearchField(device);
        countryCard = new CountryCard(device);
    }

    @Test(
            groups = {"all", "search"},
            description = "Test app is launched on Android"
    )
    public void testAppIsLaunched(){
        assertTrue(device.appExists(settings.packageId()), "Application package missing");
        if(device.appExists(settings.packageId())) {
            device.activateApp(settings.packageId());
        } else {
            device.interactor.installApp(settings.packagePath());
            device.activateApp(settings.packageId());
        }
        assertEquals(device.getAppState(settings.packageId()), ApplicationState.RUNNING_IN_FOREGROUND, "Failed to activate the app");
    }


    @Test(
            groups = {"all", "search"},
            description = "Find a country in the list",
            dependsOnMethods = {"testAppIsLaunched"}
    )
    public void testSearchInteraction() {
        // Save number of countries in the list
        countryCount = countryList.elementCount();
        // Pull down the view so the search bar is there
        if(device.deviceType == DeviceType.IOS)
            searchField.pullDown();
        searchField.searchFor(partialCountryName);
        device.wait.until(ExpectedConditions.visibilityOf(countryList.findCountryByName(countryName)));
        assertTrue(countryList.countryIsVisible(countryName));
    }

    @Test(
            groups = {"all", "search"},
            description = "Find country in the list",
            dependsOnMethods = {"testSearchInteraction"}
    )
    public void testCountryIsThere() {
        countryList.scrollToText(countryName);
        assertTrue(countryList.countryIsVisible(countryName));
    }

    @Test(
            groups = {"all", "android", "countrylist"},
            description = "Open country details",
            dependsOnMethods = {"testCountryIsThere"}
    )
    public void testCountryCardDisplay() {
        countryList.showCountry(countryName);
        device.wait.until(ExpectedConditions.visibilityOfElementLocated(countryCard.headerSelector(countryName)));
        Reporter.log("Displaying country details: [%s]. Platform %s".formatted(countryName, device.deviceType));
        assertTrue(countryCard.headerPresent(countryName));
    }

    @Test(
            groups = {"all", "android", "countrylist"},
            description = "Go back to the country list",
            dependsOnMethods = {"testCountryCardDisplay"}
    )
    public void testBackButton() {
        countryCard.goBack();
        device.wait.until(ExpectedConditions.visibilityOfElementLocated(countryCard.headerSelector(countryName)));
        Reporter.log("Back to country list. Platform %s".formatted(device.deviceType));
        assertTrue(countryList.isLoaded(), "Failed to go back to the country list");
    }

    @Test(
            groups = {"all", "android", "countrylist"},
            description = "Go back to the country list",
            dependsOnMethods = {"testBackButton"}
    )
    public void testClearButton() {
        searchField.cancelSearch();
        device.wait.until(ExpectedConditions.visibilityOf(countryList.listHeader()));
        var listItems = countryList.elementCount();
        Reporter.log("Back to country list, found %s items. Platform %s".formatted(listItems.toString(), device.deviceType));
        assertEquals(listItems, this.countryCount, "Country number mismatch");
    }
}
