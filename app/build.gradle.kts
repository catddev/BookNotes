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
            isMinifyEnabled = true
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
//    tasks.withType<Test> {
//        if (name.contains("release", ignoreCase = true)) {
//            enabled = false
//        }
//    }
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

    //core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //fragment
    implementation(libs.androidx.fragment.ktx)

    //lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.ui.util)

    //lottie
    implementation(libs.lottie.compose)

    // navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // serialization
    implementation(libs.kotlinx.serialization)

    // room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //datastore
    implementation(libs.datastore.prefs)
    implementation(libs.datastore)

    // hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt)
    implementation(libs.hilt.nav.fragment)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.crashlytics)

    //glide
    implementation(libs.glide)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization.converter)
    implementation(libs.retrofit.coroutine.call.adapter)

    //okhttp
    implementation(libs.square.okhttp.logging)

    //testing
    testImplementation(libs.junit)
    testImplementation(libs.junit.params)
    testRuntimeOnly(libs.junit.launcher)
    testRuntimeOnly(libs.junit.runtime)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    kspTest(libs.hilt.test.compiler)
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