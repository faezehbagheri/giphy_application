plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kapt)
    id(Plugins.daggerHiltRoot)
    id(Plugins.parcelize)
}

android {
    namespace = "com.example.features.search"
    compileSdk = ConfigurationData.compileSdk
    defaultConfig {
        minSdk = ConfigurationData.minSdk
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":libraries:designsystem"))
    implementation(project(":libraries:utils"))
    implementation(project(":libraries:common"))

    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.lifecycleRunTime)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.toolingPreview)
    debugImplementation(Libs.AndroidX.Compose.tooling)
    implementation(Libs.AndroidX.Compose.material)
    testImplementation(Libs.Test.junit)

    ///Hilt
    implementation(Libs.Hilt.hilt)
    kapt(Libs.Hilt.hiltCompiler)
    implementation(Libs.Hilt.hiltNavigationCompose)

    ///Timber
    implementation(Libs.Timber.timber)

    // Parcelize
    implementation(Libs.Parcelize.parcelize)

    ///Coil
    implementation(Libs.Coil.coilCompose)
    implementation(Libs.Coil.coilGif)

    ///Pagination
    implementation(Libs.Pagination.paging)
    implementation(Libs.Pagination.pagingCompose)

}
