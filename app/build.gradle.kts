plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.example.agiliwell"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.agiliwell"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {

    // ======================
    // Jetpack Compose (BOM)
    // ======================
    implementation(platform("androidx.compose:compose-bom:2024.04.00"))

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // ======================
    // AndroidX Core
    // ======================
    implementation("androidx.core:core-ktx:1.13.1")

    // ======================
    // Lifecycle & ViewModel
    // ======================
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // ======================
    // Activity Compose
    // ======================
    implementation("androidx.activity:activity-compose:1.9.1")

    // ======================
    // Room Database
    // ======================
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // ======================    b5fvr
    // WorkManager
    // ======================
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // ======================
    // Kotlin STD
    // ======================
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.25")
}
