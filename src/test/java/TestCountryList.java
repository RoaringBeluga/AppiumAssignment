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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class TestCountryList {
    private final CountryCard countryCard;
    private final CountryList countryList;

    public Device device;

    private final String countryName = "Argentina, SA";
    private final String scrollCountryName = "Brazil, SA";

    private final SettingsProvider settings = new FileSettings("config.yaml");

    @Factory(
            dataProvider = "DeviceTypes",
            dataProviderClass = DeviceTypeProvider.class
    )
    public TestCountryList(DeviceType deviceType) {
        this.settings.setDeviceType(deviceType);
        this.device = new Device(this.settings);
        this.countryCard = new CountryCard(device);
        this.countryList = new CountryList(device);
    }

    @AfterClass
    public void tearDown() {
        this.device.shutDown();
    }

    @Test(
            groups = {"all", "countrylist"},
            description = "Test app is launched",
            singleThreaded = true
    )
    public void testAppIsLaunched(){
        Reporter.log("Test app is being launched. Platform %s".formatted(device.deviceType));
        assertTrue(device.appExists(settings.packageId()), "Application package missing");
        if(device.appExists(settings.packageId())) {
            device.activateApp(settings.packageId());
            Reporter.log("Test app activated. Platform %s".formatted(device.deviceType));
        } else {
            device.interactor.installApp(settings.packagePath());
            device.activateApp(settings.packageId());
        }
        assertEquals(device.getAppState(settings.packageId()), ApplicationState.RUNNING_IN_FOREGROUND, "Failed to activate the app");
        Reporter.log("Test app is launched: Platform %s".formatted(device.deviceType));
    }

    @Test(
            groups = {"all", "countrylist"},
            description = "App is displayed correctly",
            dependsOnMethods = {"testAppIsLaunched"}
    )
    public void testCorrectDisplay() {
        assertTrue(countryList.isLoaded(), "Country list failed to load");
        Reporter.log("Checking country list. Platform %s".formatted(device.deviceType));
    }

    @Test(
            groups = {"all", "android", "countrylist"},
            description = "Open country details",
            dependsOnMethods = {"testCorrectDisplay"}
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
            groups = {"all", "countrylist"},
            description = "Can scroll down to a country",
            dependsOnMethods = {"testBackButton"}
    )
    public void testScrollToCountry() {
        Reporter.log("Scrolling country list to: [%s]. Platform %s".formatted(scrollCountryName, device.deviceType));
        countryList.scrollToText(scrollCountryName);
        device.wait.until(ExpectedConditions.visibilityOf(countryList.findCountryByName(scrollCountryName)));
        assertTrue(countryList.countryIsVisible(scrollCountryName), "Scrolling the list failed");
    }

}
