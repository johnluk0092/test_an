plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "vn.edu.hutech.computerstore"
    compileSdk = 34

    defaultConfig {
        applicationId = "vn.edu.hutech.computerstore"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    implementation(libs.lombok)
    implementation(libs.hilt.android)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation(libs.firebase.firestore)
    implementation(libs.glide)
    implementation(libs.firebase.auth)
    implementation(libs.room.runtime)
    implementation(libs.commons.text)
    annotationProcessor(libs.lombok)
    annotationProcessor(libs.room.compiler)
    annotationProcessor(libs.hilt.android.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
