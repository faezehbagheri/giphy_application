plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

android {
    namespace = "com.example.libraries.designsystem"
    compileSdk = ConfigurationData.compileSdk
    defaultConfig {
        minSdk = ConfigurationData.minSdk
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.lifecycleRunTime)
    implementation(Libs.AndroidX.Activity.activityCompose)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.toolingPreview)
    debugImplementation(Libs.AndroidX.Compose.tooling)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.foundation)
    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.testJunit)
    androidTestImplementation(Libs.AndroidX.Test.espresso)
    androidTestImplementation(Libs.AndroidX.Compose.uiTestJunit)
    debugImplementation(Libs.AndroidX.Compose.uiTestManifest)

}
