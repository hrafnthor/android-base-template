plugins {
    id("com.android.application")
    id("kotlin-android")
    id("hu.supercluster.paperwork")
}

android {
    compileSdk = Integer.parseInt(catalog.versions.android.sdk.compile.get())
    defaultConfig {
        applicationId = "is.hth.app"
        minSdk = Integer.parseInt(catalog.versions.android.sdk.min.get())
        targetSdk = Integer.parseInt(catalog.versions.android.sdk.target.get())
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"

        vectorDrawables {
            // Turns off PNG generation for versions lower than 21, rather
            // using the support libraries so vector drawables will always
            // be used.
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = catalog.versions.androidx.compose.get()
    }

    packagingOptions {
        resources {
            excludes.apply {
                add("/META-INF/{AL2.0,LGPL2.1}")
            }
        }
    }
}

dependencies {
    implementation(project(":core"))

    //#region: Android/x base

    implementation(catalog.androidx.core)
    implementation(catalog.androidx.appcompat)
    implementation(catalog.android.material)

    //#endregion

    //#region: Androidx compose

    implementation(catalog.androidx.compose.ui)
    implementation(catalog.androidx.compose.material)
    implementation(catalog.androidx.compose.preview)
    implementation(catalog.androidx.compose.activity)
    debugImplementation(catalog.androidx.compose.tooling)

    //#endregion

    implementation(catalog.androidx.lifecycle.runtime)

    //#region: Utilities

    implementation(catalog.paperwork)

    //#endregion

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