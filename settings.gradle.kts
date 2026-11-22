pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "NavigationDrawerDemo"

val hasSdk = System.getenv("ANDROID_HOME") != null || file("local.properties").exists()
if (hasSdk) include(":app")
include(":desktop-preview")
