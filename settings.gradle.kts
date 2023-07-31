pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
        mavenCentral()
    }
}

rootProject.name = "ParticleHelper"

plugins {
    id("com.pablisco.gradle.auto.include") version "1.3"
}
