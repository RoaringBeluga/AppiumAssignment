# Notes

## Preparations

* Caution required when building Android app as "Android Studio Meerkat Feature Drop | 2024.3.2" is insisting on
  updating the Android Gradle plugin, bringing up the JVM version to 21 and breaking the build in the process. Using the
  project as is solves the problems.
* Building the iOS sample failed when copying the resource `LaunchScreen.storyboard` which is caused by the said
  resource being actually named `LaunchScreen.storyboard.xml`. Renaming the file solves the issue.