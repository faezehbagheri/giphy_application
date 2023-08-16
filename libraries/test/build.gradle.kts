plugins {
    id(Plugins.androidLibrary)
}

android {
    namespace = "com.example.test"
    compileSdk = ConfigurationData.compileSdk
}

dependencies {

    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.Compose.material)
    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.testJunit)
}