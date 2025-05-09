package device;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.time.Duration;

public class Device {
    public final DeviceType deviceType;
    public final FluentWait<AppiumDriver> wait;
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
                                .usingAnyFreePort()
                                .withTimeout(Duration.ofSeconds(360))
                );
        this.service.start();
        this.driver = switch (settings.deviceType()) {
            case DeviceType.ANDROID -> {
                var options = new UiAutomator2Options()
                        .setUiautomator2ServerInstallTimeout(Duration.ofSeconds(300))
                        .setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(300))
                        .setAdbExecTimeout(Duration.ofSeconds(300));
                if (settings.wipeDevice()) options.fullReset();
                if (!settings.packagePath().isBlank()) options.setApp(settings.packagePath());
                if(settings.isVirtual()) {
                    options.setAvd(settings.deviceId());
                } else {
                    options.setUdid(settings.deviceId());
                }
                options
                        .setNewCommandTimeout(Duration.ofSeconds(120))
                        .setAvdLaunchTimeout(Duration.ofSeconds(240))
                        .setAvdReadyTimeout(Duration.ofSeconds(240));
                yield new AndroidDriver(service, options);
            }
            case DeviceType.IOS -> {
                var options = new XCUITestOptions()
                        .setUdid(settings.deviceId())
                        .setIsHeadless(settings.headless())
                        .setNewCommandTimeout(Duration.ofSeconds(120));
                if (settings.wipeDevice()) options.fullReset();
                if (!settings.packagePath().isBlank()) options.setApp(settings.packagePath());
                yield new IOSDriver(service, options);
            }
        };
        this.deviceType = settings.deviceType();
        this.interactor = (InteractsWithApps) this.driver;
        this.wait = new AppiumFluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(settings.waitTimeout()))
                .pollingEvery(Duration.ofSeconds(settings.pollingInterval()))
                .ignoring(NoSuchElementException.class);
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

