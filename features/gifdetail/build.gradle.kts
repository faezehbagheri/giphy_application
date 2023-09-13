plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kapt)
    id(Plugins.daggerHiltRoot)
    id(Plugins.parcelize)
}

android {
    namespace = "com.example.features.gifdetail"
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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.Libraries.designSystem))
    implementation(project(Modules.Libraries.navigation))
    implementation(project(Modules.Libraries.utils))
    implementation(project(Modules.Libraries.common))
    implementation(project(Modules.Libraries.test))

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

    ///Test
    testImplementation(Libs.Test.junit)
    testImplementation(Libs.AndroidX.Compose.Test.composeUiTest)
    testImplementation(Libs.Test.robolectric)
    testImplementation(Libs.Test.mockk)

}
