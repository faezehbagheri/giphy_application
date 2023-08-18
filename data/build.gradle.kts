import java.util.Properties

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kapt)
    id(Plugins.daggerHiltRoot)
    id(Plugins.parcelize)
}

android {
    namespace = "com.example.data"
    compileSdk = ConfigurationData.compileSdk
    defaultConfig {
        minSdk = ConfigurationData.minSdk
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    defaultConfig {
        val projectProperties = readProperties(file("../local.properties"))

        buildConfigField("String", "API_KEY", projectProperties["MY_KEY"] as String)
        buildConfigField("String", "BASE_URL", projectProperties["BASE_URL"] as String)
    }
}

fun readProperties(propertiesFile: File) = Properties().apply {
    propertiesFile.inputStream().use { fis ->
        load(fis)
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":libraries:common"))

    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.appcompat)

    ///Hilt
    implementation(Libs.Hilt.hilt)
    kapt(Libs.Hilt.hiltCompiler)
    implementation(Libs.Hilt.hiltNavigationCompose)

    ///Retrofit
    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.converterGson)
    implementation(Libs.Okhttp.okhttp)
    implementation(Libs.Okhttp.interceptor)

    // Parcelize
    implementation(Libs.Parcelize.parcelize)

    ///Pagination
    implementation(Libs.Pagination.paging)
    implementation(Libs.Pagination.pagingCompose)

    ///Test
    testImplementation(Libs.Test.mockk)
    testImplementation(Libs.Test.junit)
    testImplementation(Libs.Test.coroutines)
    testImplementation(Libs.Okhttp.mockWebServer)
    testImplementation(Libs.Hilt.Test.hiltTest)
    testImplementation(Libs.Test.robolectric)
    kaptTest(Libs.Hilt.hiltCompiler)
    testImplementation(project(":libraries:test"))
}
