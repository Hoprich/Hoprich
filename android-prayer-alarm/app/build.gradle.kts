plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "org.hoprich.unbroken.alarm"
    compileSdk = 35
    defaultConfig { applicationId = "org.hoprich.unbroken.alarm"; minSdk = 26; targetSdk = 35; versionCode = 1; versionName = "0.1.0" }
}
