plugins {
    `java-library`
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.jar {
    classifier = "api"
}