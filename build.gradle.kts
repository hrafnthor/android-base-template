import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import app.DependencyUpdates

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.0.0")
        // Used for build info generation without breaking incremental compilation
        classpath("hu.supercluster:paperwork-plugin:1.2.7")
    }
}

plugins {
    // Used for dependency update checking
    id("com.github.ben-manes.versions") version "0.38.0"
    // Used for running enforced code styles as part of Gradle builds
    id("com.diffplug.gradle.spotless") version "4.3.0"
    // Used for checking project dependency health via Gradle using 'buildHealth'
    id("com.autonomousapps.dependency-analysis") version "0.78.0"
}

allprojects {
    apply {
        plugin("com.diffplug.gradle.spotless")
    }
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            ktlint("0.42.1")
            // Applies licensing header to all applicable .kt files
            // licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }
    }

    gradle.projectsEvaluated {
        // Enforce Java compile configurations across all projects
        tasks.withType(JavaCompile::class).configureEach {
            sourceCompatibility = JavaVersion.VERSION_11.toString()
            targetCompatibility = JavaVersion.VERSION_11.toString()
        }

        // Enforce Kotlin compile configurations across all projects
        tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {
            kotlinOptions {
                // Treat all Kotlin warnings as errors
                allWarningsAsErrors = true

                // Set JVM target to 11
                jvmTarget = JavaVersion.VERSION_11.toString()

                sourceCompatibility = JavaVersion.VERSION_11.toString()
                targetCompatibility = JavaVersion.VERSION_11.toString()
            }
        }
    }
}

tasks {
    withType<Test> {
        // For kotest Junit5 compatibility
        useJUnitPlatform()
    }

    /**
     * Update dependencyUpdates task to reject versions which are more 'unstable' than our
     * current version.
     */
    named("dependencyUpdates", DependencyUpdatesTask::class).configure {
        checkConstraints = true
        checkForGradleUpdate = true
        rejectVersionIf {
            val current = DependencyUpdates.versionToRelease(currentVersion)
            // If we're using a SNAPSHOT, ignore since we must be doing so for a reason.
            if (current == app.ReleaseType.SNAPSHOT) {
                true
            } else {
                // Otherwise we reject if the candidate is more 'unstable' than our version
                val candidate = DependencyUpdates.versionToRelease(candidate.version)
                candidate.isLessStableThan(current)
            }
        }
    }
}