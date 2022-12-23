plugins {
    id("studycompose.android.application")
    id("studycompose.android.application.compose")
}

android {
    defaultConfig {
        applicationId = "com.example.week3"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material)

    testImplementation (libs.junit4)

    androidTestImplementation (libs.androidx.test.ext)
    androidTestImplementation (libs.androidx.compose.ui.test)
    androidTestImplementation (libs.androidx.test.espresso.core)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    //image load lib
    implementation("io.coil-kt:coil-compose:1.4.0")

    //Constraint Layout - Compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc02")
}