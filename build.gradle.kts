plugins {
    java
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }
}

val nmsProject = project(":nms:nms-impl")
dependencies {
    // include all impls
    for (child in nmsProject.childProjects) {
        implementation(project(child.value.path, configuration = "reobf"))
    }

    implementation(project(":api"))
    implementation(project(":nms:nms-interface"))
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
        repositories {
            maven {
                url = uri("https://maven.pkg.github.com/LemonGamingLtd/ParticleHelper")
                credentials {
                    // sadly, we have to do this until fine-grained tokens get support for GitHub Packages
                    username = providers.gradleProperty("lgGithubUser").getOrElse(System.getenv("GITHUB_ACTOR"))
                    password = providers.gradleProperty("lgGithubToken").getOrElse(System.getenv("GITHUB_TOKEN"))
                }
            }
        }

        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                artifactId = providers.gradleProperty("name").get() + "-" + project.name
            }
        }
    }
}

publishing {
    publications {
        getByName<MavenPublication>("mavenJava") {
            artifactId = providers.gradleProperty("name").get()
        }
    }
}