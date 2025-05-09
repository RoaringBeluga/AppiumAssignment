package device;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;

public class Device {
    public final DeviceType deviceType;
    public AppiumDriver driver;
    private final AppiumDriverLocalService service;
    public InteractsWithApps interactor;

    public Device(SettingsProvider settings) {
        this.service = AppiumDriverLocalService
                .buildService(
                        new AppiumServiceBuilder()
                                .withAppiumJS(
                                        new File(settings.appiumPath())
                                )
                );
        this.service.start();
        this.driver = switch (settings.deviceType()) {
            case DeviceType.ANDROID -> {
                var options = new UiAutomator2Options();
                if (settings.wipeDevice()) options.fullReset();
                if (!settings.packagePath().isBlank()) options.setApp(settings.packagePath());
                if(settings.isVirtual()) {
                    options.setAvd(settings.deviceId());
                } else {
                    options.setUdid(settings.deviceId());
                }
                yield new AndroidDriver(service, options);
            }
            case DeviceType.IOS -> {
                var options = new XCUITestOptions().setUdid(settings.deviceId());
                if (settings.wipeDevice()) options.fullReset();
                if (!settings.packagePath().isBlank()) options.setApp(settings.packagePath());
                yield new IOSDriver(service, options);
            }
        };
        this.deviceType = settings.deviceType();
        this.interactor = (InteractsWithApps) this.driver;
    }

    public void shutDown() {
        this.driver.quit();
        service.stop();
    }

    public boolean appExists(String appPackage) {
        return this.interactor.isAppInstalled(appPackage);
    }

    public void activateApp(String appPackage) {
        interactor.activateApp(appPackage);
    }

    public ApplicationState getAppState(String appPackage) {
        return interactor.queryAppState(appPackage);
    }
}

