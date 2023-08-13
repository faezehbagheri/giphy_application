object Libs {
    const val androidApplication = "com.android.application:8.0.1"
    const val androidLibrary = "com.android.library:8.0.1"
    const val kotlinAndroid = "org.jetbrains.kotlin.android:1.7.0"

    object AndroidX {

        const val core = "androidx.core:core-ktx:1.7.0"
        const val lifecycleRunTime = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
        const val annotation = "androidx.annotation:annotation:1.6.0"
        const val appcompat = "androidx.appcompat:appcompat:1.6.1"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.1"
        }

        object Compose {
            private const val compose_version = "1.5.0"

            const val runtime = "androidx.compose.runtime:runtime:$compose_version"
            const val foundation = "androidx.compose.foundation:foundation:${compose_version}"

            const val material = "androidx.compose.material:material:1.2.0"

            const val ui = "androidx.compose.ui:ui:${compose_version}"
            const val tooling = "androidx.compose.ui:ui-tooling:${compose_version}"
            const val toolingPreview =
                "androidx.compose.ui:ui-tooling-preview:${compose_version}"
            const val uiTestJunit = "androidx.compose.ui:ui-test-junit4:$compose_version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$compose_version"

            object Test {
                const val composeUiTest = "androidx.compose.ui:ui-test-junit4:$compose_version"
                const val composeTestManifest = "androidx.compose.ui:ui-test-manifest:$compose_version"
            }
        }

        object Test {
            const val testJunit = "androidx.test.ext:junit:1.1.5"
        }
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val mockk = "io.mockk:mockk:1.13.5"
    }

    object Hilt {
        private const val version = "2.47"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:$version"
        const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.1.0-alpha01"
    }

    object JavaX {
        const val inject = "javax.inject:javax.inject:1"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
        const val okhttp = "com.squareup.okhttp3:okhttp:4.9.3"
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6"
    }

    object Gson {
        const val gson = "com.google.code.gson:gson:2.9.0"
    }

    object Timber {
        private const val version = "4.7.1"
        const val timber = "com.jakewharton.timber:timber:$version"
    }

    object Navigation {
        const val navigation = "androidx.navigation:navigation-compose:2.5.0"
    }

    object Parcelize {
        const val parcelize = "org.jetbrains.kotlin:kotlin-parcelize-runtime:1.7.0"
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:2.2.2"
        const val coilGif = "io.coil-kt:coil-gif:2.2.2"
    }

    object Pagination {
        private const val version = "3.1.1"
        const val pagingCommon = "androidx.paging:paging-common:$version"
        const val paging = "androidx.paging:paging-runtime:$version"
        const val pagingCompose = "androidx.paging:paging-compose:3.2.0"
    }
}