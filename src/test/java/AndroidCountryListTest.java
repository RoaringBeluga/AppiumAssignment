import device.Device;
import device.DeviceType;
import device.FileSettings;
import device.SettingsProvider;
import io.appium.java_client.appmanagement.ApplicationState;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import screens.CountryCard;
import screens.CountryList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class AndroidCountryListTest {
    public Device device;

    private final SettingsProvider settings = new FileSettings("config.yaml");

    @BeforeClass
    public void setUp() {
        this.settings.setDeviceType(DeviceType.ANDROID);
        this.device = new Device(this.settings);
    }

    @Test(
            groups = {"all", "android", "countrylist"},
            description = "Test app is launched on Android"
    )
    public void testAppIsLaunched(){
        assertTrue(device.appExists(settings.packageId()), "Application package missing");
        if(device.appExists(settings.packageId()))
            device.activateApp(settings.packageId());
        assertEquals(device.getAppState(settings.packageId()), ApplicationState.RUNNING_IN_FOREGROUND, "Failed to activate the app");
    }

    @Test(
            groups = {"all", "android", "countrylist"},
            description = "Find Afghanistan in the list",
            dependsOnMethods = {"testAppIsLaunched"}
    )
    public void testBrazilIsThere() {
        var countryList = new CountryList(device);
        countryList.scrollToText("Brazil, SA");
        assertTrue(countryList.countryIsVisible("Brazil, SA"));
    }

    @Test(
            groups = {"all", "android", "countrylist"},
            description = "Open country details",
            dependsOnMethods = {"testBrazilIsThere"}
    )
    public void testCountryCardDisplay() {
        var countryList = new CountryList(device);
        countryList.showCountry("Brazil, SA");
        var countryCard = new CountryCard(device);
        assertTrue(countryCard.headerPresent("Brazil, SA"));
    }

    @Test(
            groups = {"all", "android", "countrylist"},
            description = "Go back to the country list",
            dependsOnMethods = {"testCountryCardDisplay"}
    )
    public void testBackButton() {
        var countryCard = new CountryCard(device);
        countryCard.goBack();
        var countryList = new CountryList(device);
        assertTrue(countryList.isLoaded(), "Failed to go back to the country list");
    }
}
