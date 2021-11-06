// The dependency resolution management feature used below is
// currently an incubating feature and needs to be opted into
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("catalog") {
            //#region: Kotlin

            val kotlinVersion: String by settings
            val kotlinVersionRef = version("kotlin", kotlinVersion)
            alias("kotlin-gradle")
                .to("org.jetbrains.kotlin", "kotlin-gradle-plugin")
                .versionRef(kotlinVersionRef)

            alias("kotlin-reflect")
                .to("org.jetbrains.kotlin", "kotlin-reflect")
                .versionRef(kotlinVersionRef)

            //#endregion

            //#region: Android

            //#region: SDK

            val androidCompileSdkVersion: String by settings
            version("android-sdk-compile", androidCompileSdkVersion)

            val androidTargetSdkVersion: String by settings
            version("android-sdk-target", androidTargetSdkVersion)

            val androidMinSdkVersion: String by settings
            version("android-sdk-min", androidMinSdkVersion)
            
            //#endregion

            val androidMaterialVersion: String by settings
            alias("android-material")
                .to("com.google.android.material", "material")
                .versionRef(version("android-material", androidMaterialVersion))

            //#endregion

            //#region: Androidx

            val androidxCoreVersion: String by settings
            alias("androidx-core")
                .to("androidx.core", "core-ktx")
                .versionRef(version("androidx-core", androidxCoreVersion))

            val androidxAppcompatVersion: String by settings
            alias("androidx-appcompat")
                .to("androidx.appcompat", "appcompat")
                .versionRef(version("androidx-appcompat", androidxAppcompatVersion))

            //#region: Lifecycle

            val androidxLifecycleVersion: String by settings
            alias("androidx-lifecycle-runtime")
                .to("androidx.lifecycle", "lifecycle-runtime-ktx")
                .versionRef(version("androidx-lifecycle", androidxLifecycleVersion))

            //#endregion

            //#region: Compose

            val androidxComposeVersion: String by settings
            val androidxComposeVersionRef = version(
                "androidx-compose",
                androidxComposeVersion
            )

            alias("androidx-compose-ui")
                .to("androidx.compose.ui", "ui")
                .versionRef(androidxComposeVersionRef)

            alias("androidx-compose-material")
                .to("androidx.compose.material", "material")
                .versionRef(androidxComposeVersionRef)

            alias("androidx-compose-preview")
                .to("androidx.compose.ui", "ui-tooling-preview")
                .versionRef(androidxComposeVersionRef)

            alias("androidx-compose-tooling")
                .to("androidx.compose.ui", "ui-tooling")
                .versionRef(androidxComposeVersionRef)

            alias("androidx-test-compose-ui")
                .to("androidx.compose.ui", "ui-test-junit4")
                .versionRef(androidxComposeVersionRef)

            //#region: UI

            val androidxActivityComposeVersion: String by settings
            alias("androidx-compose-activity")
                .to("androidx.activity", "activity-compose")
                .versionRef(version("androidx-activity-compose", androidxActivityComposeVersion))

            //#endregion

            //#endregion

            //#endregion

            //#region: Testing

            //#region: JVM

            //#region JUnit5

            val junit5Version: String by settings
            val junit5 = version("junit5", junit5Version)
            alias("junit5-api")
                .to("org.junit.jupiter", "junit-jupiter-api")
                .versionRef(junit5)
            alias("junit5-engine")
                .to("org.junit.jupiter", "junit-jupiter-engine")
                .versionRef(junit5)
            alias("junit5-params")
                .to("org.junit.jupiter", "junit-jupiter-params")
                .versionRef(junit5)
            alias("junit-engine")
                .to("org.junit.vintage", "junit-vintage-engine")
                .versionRef(junit5)

            //#endregion

            //#region Mannodermaus

            val mannodermausLibraryVersion: String by settings
            val mannodermausJunit5 = version(
                "mannodermaus-junit5",
                mannodermausLibraryVersion
            )
            alias("mannodermaus-junit5-android-core")
                .to("de.mannodermaus.junit5", "android-test-core")
                .versionRef(mannodermausJunit5)
            alias("mannodermaus-junit5-android-runner")
                .to("de.mannodermaus.junit5", "android-test-runner")
                .versionRef(mannodermausJunit5)

            //#endregion

            //#region Kotest

            val kotestVersion: String by settings
            val kotest = version("kotest", kotestVersion)
            alias("kotest-assertions-core")
                .to("io.kotest", "kotest-assertions-core")
                .versionRef(kotest)
            alias("kotest-assertions-json")
                .to("io.kotest", "kotest-assertions-json")
                .versionRef(kotest)
            alias("kotest-junit5-runner")
                .to("io.kotest", "kotest-runner-junit5")
                .versionRef(kotest)
            alias("kotest-property")
                .to("io.kotest", "kotest-property")
                .versionRef(kotest)

            //#endregion

            //#endregion

            //#region: Instrumentation

            //#region AndroidX Test

            val androidxJunitExtVersion: String by settings
            alias("androidx-test-extensions-junit")
                .to("androidx.test.ext", "junit")
                .version(androidxJunitExtVersion)

            val androidxTestRunner: String by settings
            alias("androidx-test-runner")
                .to("androidx.test", "runner")
                .version(androidxTestRunner)

            val androidxEspressoVersion: String by settings
            alias("androidx-test-espresso")
                .to("androidx.test.espresso", "espresso-core")
                .versionRef(version("androidx-espresso", androidxEspressoVersion))

            //#endregion

            //#endregion
        }
    }
}

rootProject.name = "app"
include(":app")
include(":core")
