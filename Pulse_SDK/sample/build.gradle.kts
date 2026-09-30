plugins {
    alias(libs.plugins.android.application)
}

tasks.register("helloTask2") {
    group = "Other"
    description = "Hello task"
    println("Hello")
}

android {
    namespace = "dev.pulse.sample"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }
    buildFeatures {
        buildConfig = true
        resValues = true
    }

    defaultConfig {
        applicationId = "dev.pulse.sample"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    fun secret(name: String): String? =
        providers.gradleProperty(name).orElse(providers.environmentVariable(name)).orNull

    val releaseStoreFile = secret("PULSE_STORE_FILE")
    signingConfigs {
        // Only create the release config if the secrets exist on this machine
        if (releaseStoreFile != null) {
            create("release") {
                storeFile = file(releaseStoreFile)
                storePassword = secret("PULSE_STORE_PASSWORD")
                keyAlias = secret("PULSE_KEY_ALIAS")
                keyPassword = secret("PULSE_KEY_PASSWORD")
            }
        }
    }
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")

        }
    }
    flavorDimensions += "environment"
    productFlavors {
        create("staging") {
            dimension = "environment"
            applicationIdSuffix = ".staging"
            buildConfigField("String", "PULSE_ENV", "\"staging\"")
            resValue("string", "app_name", "Pulse Sample (STG)")
        }
        create("production") {
            dimension = "environment"
            buildConfigField("String", "PULSE_ENV", "\"production\"")
            resValue("string", "app_name", "Pulse Sample")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.timber)
    implementation(project(":pulse-core"))       // nhờ api(pulse-model), app dùng được PulseLevel
    implementation(project(":pulse-sink-logcat"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}

println("[CONFIG] $path configured")