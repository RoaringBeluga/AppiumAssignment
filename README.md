# Notes

## Preparations

* Caution required when building Android app as "Android Studio Meerkat Feature Drop | 2024.3.2" is insisting on
  updating the Android Gradle plugin, bringing up the JVM version to 21 and breaking the build in the process. Using the
  project as is solves the problems.
* Building the iOS sample failed when copying the resource `LaunchScreen.storyboard` which is caused by the said
  resource being actually named `LaunchScreen.storyboard.xml`. Renaming the file solves the issue.
* Due to Appium
  bugs [#15507](https://github.com/appium/appium/issues/15507), [#17279](https://github.com/appium/appium/issues/17279)
  and related bugs, Android tests were EXTREMELY flaky and worked mostly when the emulator was prepared beforehand. No
  feasible solution was found as timeouts appear to have little to no effect on the startup sequence. Possible remedy:
  Running on more powerful hardware which hopefully enables faster startup. iOS tests not affected despite simulator
  taking comparable length of time to load.

## Assumptions made

* Appium is installed with the relevant drivers
* Applications packages are built already

## Configuration
```yaml
ios: # Configuration for iOS simulator
  # Device UUID for the simulator
  device-id: "F7B77DF9-C862-4B92-AF9A-10CEB8FF367B"
  # Path to the .app package
  package-path: "/Users/username/Library/Developer/Xcode/DerivedData/CountriesChallenge-grbnenqaqpxdrububxvklshuixrv/Build/Products/Debug-iphonesimulator/CountriesChallenge.app"
  # Package ID
  package-id: "com.walmart.CountriesChallenge"
  # Whether to wipe the device
  wipe-device: false

android: # Android device configuration - should work with real devices, too
  # Device ID
  device-id: "@Medium_Phone_AOSP_API_35"
  # Ignored
  device-id-2: "emulator-5554"
  # Path to the .apk
  package-path: "/Users/username/Development/Test/TestProject-android-main/app/build/outputs/apk/debug/app-debug.apk"
  # App package ID
  package-id: "com.example.wallmartexample"
  # Whether to wipe the device
  wipe-device: false
  # Whether the device is an AVD
  avd: true

general: # General settings
  # Path to Appium's main.js
  appium-js-path: "/usr/local/Cellar/appium/2.18.0/libexec/lib/node_modules/appium/build/lib/main.js"
  # Wait timeout for AppiumDriverWait
  wait-timeout: 30
  # Polling interval for AppiumDriverWait
  polling-interval: 2
```