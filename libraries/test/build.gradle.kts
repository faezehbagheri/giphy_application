plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

android {
    namespace = "com.example.libraries.test"
    compileSdk = ConfigurationData.compileSdk
    defaultConfig {
        minSdk = ConfigurationData.minSdk
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.testJunit)
}