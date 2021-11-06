plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = Integer.parseInt(catalog.versions.android.sdk.compile.get())
    defaultConfig {
        minSdk = Integer.parseInt(catalog.versions.android.sdk.min.get())
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"
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

    kotlinOptions {
        // Turn on strict compiler flags to force a stance on library visibility
        freeCompilerArgs = freeCompilerArgs + "-Xexplicit-api=strict"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = catalog.versions.androidx.compose.get()
    }
}

dependencies {

    //#region: Testing

    //#region: JVM

    testImplementation(catalog.kotlin.reflect)
    testImplementation(catalog.junit5.api)
    testRuntimeOnly(catalog.junit5.engine)

    //#region Kotest

    testImplementation(catalog.kotest.junit5.runner)
    testImplementation(catalog.kotest.assertions.core)
    testImplementation(catalog.kotest.assertions.json)

    //#endregion

    //#endregion

    //#region: Instrumentation

    androidTestImplementation(catalog.androidx.test.compose.ui)
    androidTestImplementation(catalog.androidx.test.espresso)

    androidTestImplementation(catalog.androidx.test.runner)
    androidTestImplementation(catalog.androidx.test.extensions.junit)
    androidTestImplementation(catalog.junit5.api)

    androidTestImplementation(catalog.mannodermaus.junit5.android.core)
    androidTestRuntimeOnly(catalog.mannodermaus.junit5.android.runner)

    //#endregion

    //#endregion
}