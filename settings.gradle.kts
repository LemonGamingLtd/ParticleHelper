pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
        mavenCentral()
    }
}

rootProject.name = "particle-helper"

plugins {
    id("com.pablisco.gradle.auto.include") version "1.3"
}
