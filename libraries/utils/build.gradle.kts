plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.libraries.utils"
    compileSdk = 33
    defaultConfig {
        minSdk = ConfigurationData.minSdk
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
    testImplementation(Libs.Test.junit)
}