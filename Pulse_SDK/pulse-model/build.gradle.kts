import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
    `java-library`                  // tạo configuration api/implementation/runtimeClasspath...
    alias(libs.plugins.kotlin.jvm)  // compile Kotlin → JVM bytecode
}

group = providers.gradleProperty("pulse.group").get()
version = providers.gradleProperty("pulse.version").get()

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    explicitApi()
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)    // phải khớp với targetCompatibility
    }
}

dependencies {
    testImplementation(libs.junit)         // chỉ dùng khi test, không vào JAR
}


println("[CONFIG] $path configured")