package device;

import com.electronwill.nightconfig.core.file.FileConfig;

import java.io.File;

public class FileSettings implements SettingsProvider {
    private DeviceType selectedOs = DeviceType.IOS;
    private final FileConfig config;

    public FileSettings(String fileName) {
        File configFile = new File(fileName);
        this.config = FileConfig.of(configFile);
        config.load();

    }

    public void setDeviceType(DeviceType type) {
        this.selectedOs = type;
    }

    @Override
    public DeviceType deviceType() {
        return this.selectedOs;
    }

    @Override
    public String deviceId() {
        return switch(this.selectedOs) {
            case IOS -> config.getOrElse("ios.device-id", "NA");
            case ANDROID -> config.getOrElse("android.device-id", "NA");
        };
    }

    @Override
    public String packagePath() {
        return switch(this.selectedOs) {
            case IOS -> config.getOrElse("ios.package-path", "NA");
            case ANDROID -> config.getOrElse("android.package-path", "NA");
        };
    }

    @Override
    public String packageId() {
        return switch(this.selectedOs) {
            case IOS -> config.getOrElse("ios.package-id", "NA");
            case ANDROID -> config.getOrElse("android.package-id", "NA");
        };
    }

    @Override
    public String appiumPath() {
        return config.getOrElse("general.appium-js-path", "");
    }

    @Override
    public boolean wipeDevice() {
        return switch(this.selectedOs) {
            case IOS -> config.getOrElse("ios.wipe-device", false);
            case ANDROID -> config.getOrElse("android.wipe-device", false);
        };
    }

    @Override
    public boolean isVirtual() {
        return switch(this.selectedOs) {
            case IOS -> config.getOrElse("ios.simulator", false);
            case ANDROID -> config.getOrElse("android.avd", false);
        };

    }

    @Override
    public boolean headless() {
        return switch(this.selectedOs) {
            case IOS -> config.getOrElse("ios.headless", false);
            case ANDROID -> config.getOrElse("android.headless", false);
        };

    }

    @Override
    public long waitTimeout() {
        return config.getLongOrElse("general.wait-timeout", 30);
    }

    @Override
    public long pollingInterval() {
        return config.getLongOrElse("general.polling-interval", 2);
    }
}
