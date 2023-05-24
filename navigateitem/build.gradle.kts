plugins {
    id("studycompose.android.application")
    id("studycompose.android.application.compose")
    kotlin("kapt")
//    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.navigateitem"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.navigateitem"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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


    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation("com.squareup.moshi:moshi-kotlin-codegen:1.9.3")

    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
//    implementation(libs.androidx.hilt.navigation.compose)
//
//    implementation(libs.hilt.android)
//    implementation(libs.hilt.ext.work)
//    implementation(libs.hilt.ext.compiler)
//    implementation(libs.hilt.compiler)

}