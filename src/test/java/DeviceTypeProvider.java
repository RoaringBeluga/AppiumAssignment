import device.DeviceType;
import org.testng.annotations.DataProvider;

public class DeviceTypeProvider {
    @DataProvider(name = "DeviceTypes")
    public DeviceType[] deviceTypes() {
        return new DeviceType[] {
            DeviceType.IOS,
            DeviceType.ANDROID
        };
    }
}
