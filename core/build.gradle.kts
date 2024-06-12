plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.epamupskills.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    //core
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)
    api(libs.androidx.activity)
    api(libs.androidx.constraintlayout)

    //fragment
    api(libs.androidx.fragment.ktx)

    //lifecycle
    api(libs.androidx.lifecycle.runtime.ktx)

    //compose
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.activity.compose)
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)
    debugApi(libs.androidx.ui.tooling)
    debugApi(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.ui.util)

    //lottie
    api(libs.lottie.compose)

    // navigation
    api(libs.navigation.fragment.ktx)
    api(libs.navigation.ui.ktx)

    // serialization
    api(libs.kotlinx.serialization)

    // room
    ksp(libs.androidx.room.compiler)
    api(libs.androidx.room)
    annotationProcessor(libs.androidx.room.compiler)
    api(libs.androidx.room.ktx)

    //datastore
    api(libs.datastore.prefs)
    api(libs.datastore)

    // hilt
    ksp(libs.hilt.compiler)
    api(libs.hilt)
    api(libs.hilt.nav.fragment)

    //firebase
    api(platform(libs.firebase.bom))
    api(libs.firebase.analytics)
    api(libs.firebase.crashlytics)

    //glide
    api(libs.glide)

    //retrofit
    api(libs.retrofit)
    api(libs.retrofit.serialization.converter)
    api(libs.retrofit.coroutine.call.adapter)

    //okhttp
    api(libs.square.okhttp.logging)
}