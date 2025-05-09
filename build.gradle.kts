plugins {
    id("java")
}

group = "net.nowherelands"
version = "1.0-SNAPSHOT"

val libVersions = mapOf(
    "appium-java" to "9.4.0",
    "junit" to "5.10.0",
    "config" to "3.6.0",
    "testng" to "7.9.0"
)

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.electronwill.night-config:yaml:${libVersions["config"]}")
//    testImplementation(platform("org.junit:junit-bom:${libVersions["junit"]}"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.testng:testng:${libVersions["testng"]}")
    testImplementation("io.appium:java-client:${libVersions["appium-java"]}")
}

tasks.test {
    //useJUnitPlatform()
    useTestNG()
}