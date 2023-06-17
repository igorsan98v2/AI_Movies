// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.30"
    kotlin("kapt") version "1.8.0"
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

buildscript {
    // ...
    dependencies {
        // ...
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }
}
