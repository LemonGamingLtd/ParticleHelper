plugins {
    java
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "ltd.lemongaming"
version = "1.5.0"

val nmsProject = project(":nms:nms-impl")
dependencies {
    // include all impls
    for (child in nmsProject.childProjects) {
        implementation(project(child.value.path, configuration = "reobf"))
    }

    implementation(project(":api"))
    implementation(project(":nms:nms-interface"))
}

tasks {
    shadowJar {
        dependencies {
            relocate("org.bstats", "com.owen1212055.${rootProject.name}.libs.bstats")
        }

        archiveClassifier.set("")
        archiveBaseName.set("${project.name}")
    }

    assemble {
        dependsOn("shadowJar")
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }

    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }
    }
}