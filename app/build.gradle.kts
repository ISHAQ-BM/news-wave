import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.newswave"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
        compose =true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.newswave"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "BASE_URL", "\"${properties.getProperty("BASE_URL")}\"")
        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")

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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Dagger - Hilt

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // LiveData
    implementation (libs.androidx.lifecycle.livedata.ktx)

    //intuit library ..sdp/ssp
    implementation (libs.sdp.android)
    implementation (libs.ssp.android)

    //splash screen
    implementation(libs.androidx.core.splashscreen)

    //Room
    implementation (libs.androidx.room.runtime)
    kapt ("androidx.room:room-compiler:2.6.1")
    // Kotlin Extensions and Coroutines support for Room
    implementation (libs.androidx.room.ktx)

    // Coil
    implementation (libs.coil)
    implementation(libs.coil.compose)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)


    // Preferences DataStore (SharedPreferences like APIs)
    implementation (libs.androidx.datastore.preferences)

    // Compose
    implementation(platform(libs.androidx.compose.bom))



    implementation (libs.androidx.runtime)
    implementation (libs.androidx.ui)
    implementation (libs.androidx.foundation)
    implementation (libs.androidx.foundation.layout)
    implementation (libs.androidx.material3)
    implementation (libs.androidx.runtime.livedata)
    implementation (libs.androidx.ui.tooling)






}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}