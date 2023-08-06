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
    implementation(project(":common"))

    implementation(Libs.AndroidX.core)
    testImplementation(Libs.AndroidX.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.testJunit)
    androidTestImplementation(Libs.AndroidX.Test.espresso)
    implementation(Libs.AndroidX.appcompat)

    ///Hilt
    implementation(Libs.Hilt.hilt)
    kapt(Libs.Hilt.hiltCompiler)
    implementation(Libs.Hilt.hiltNavigationCompose)

    ///Retrofit
    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.converterGson)
    implementation(Libs.Retrofit.okhttp)
    implementation(Libs.Retrofit.interceptor)

    // Parcelize
    implementation(Libs.Parcelize.parcelize)

    ///Pagination
    implementation(Libs.Pagination.paging)
    implementation(Libs.Pagination.pagingCompose)

}
