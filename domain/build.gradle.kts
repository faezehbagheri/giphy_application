plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kapt)
    id(Plugins.daggerHiltRoot)
}

android {
    namespace = "com.example.domain"
    compileSdk = ConfigurationData.compileSdk

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
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

    ///Pagination
    implementation(Libs.Pagination.paging)
    implementation(Libs.Pagination.pagingCompose)
}