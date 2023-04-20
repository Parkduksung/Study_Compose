plugins {
    id("studycompose.android.application")
    id("studycompose.android.application.compose")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        applicationId = "com.example.viewpager"
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

    testImplementation(libs.junit4)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.test.espresso.core)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    implementation(libs.androidx.compose.material.iconsExtended)

    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicator)

    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    val versions = "2.44"

    // hilt
    implementation("com.google.dagger:hilt-android:$versions")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-compiler:$versions")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$versions")
    kaptAndroidTest("com.google.dagger:hilt-compiler:$versions")
}