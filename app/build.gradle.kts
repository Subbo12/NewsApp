plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id ("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.9.24"
}

android {
    namespace = "com.example.newsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.newsapp"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Core
    implementation("androidx.core:core-ktx:1.16.0")

    implementation ("io.coil-kt:coil-compose:2.7.0")

    implementation ("androidx.navigation:navigation-compose:2.8.9") // برای ناوبری

    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1") // برای سریال‌سازی

    implementation ("androidx.compose.material3:material3:1.3.0")

    implementation ("androidx.datastore:datastore-preferences:1.1.4")


    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1") // ViewModel KTX
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1") // LiveData KTX



    implementation ("com.google.dagger:hilt-android:2.56")
    kapt ("com.google.dagger:hilt-compiler:2.56")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")


    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation("androidx.compose.material:material:1.7.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.8")
    implementation("androidx.activity:activity-compose:1.10.1")

    // Retrofit for network requests
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")


    // Coroutine Dependencies
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
}