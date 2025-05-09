package device;

public interface SettingsProvider {
    DeviceType deviceType();
    String deviceId();
    String packagePath();
    String packageId();
    String appiumPath();
    boolean wipeDevice();
    boolean isVirtual();

    void setDeviceType(DeviceType deviceType);

    boolean headless();

    long waitTimeout();

    long pollingInterval();
}
