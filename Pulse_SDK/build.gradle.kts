// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
}

val demo = providers.gradleProperty("pulse.demo").getOrElse("none")
println("[CONFIG] pulse.demo = $demo")

println("[CONFIG] root project configured")
tasks.register("lifecycleDemo") {
    group = "pulse"
    description = "Prints the order of Gradle build phases."
    println("[CONFIG] lifecycleDemo is being CONFIGURED")
    doFirst { println("[EXEC]   lifecycleDemo doFirst") }
    doLast { println("[EXEC]   lifecycleDemo doLast") }
}

tasks.matching { it.name.contains("Kotlin") }.configureEach {
    println("[CONFIG] $name -> ${javaClass.name}")
}