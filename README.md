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