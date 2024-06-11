plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.nav.safe.args)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.epamupskills.booknotes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.epamupskills.booknotes"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.epamupskills.booknotes.HiltTestRunner"
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://www.googleapis.com/books/\"")
            buildConfigField("String", "API_KEY", "\"AIzaSyDC-0PS_IjUp1eH1yMA6_0hJtHmn86Bhos\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://www.googleapis.com/books/\"")
            buildConfigField("String", "API_KEY", "\"AIzaSyDC-0PS_IjUp1eH1yMA6_0hJtHmn86Bhos\"")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":authorization"))
    implementation(project(":book_notes"))

    // hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt)

    //testing
    testImplementation(libs.junit)
    testImplementation(libs.junit.params)
    testRuntimeOnly(libs.junit.launcher)
    testRuntimeOnly(libs.junit.runtime)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestDebugImplementation(libs.androidx.test.core)
    debugImplementation(libs.fragment.testing)
    debugImplementation(libs.navigation.testing)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.test.compiler)
    androidTestImplementation(project(":core"))
    androidTestImplementation(project(":authorization"))
}