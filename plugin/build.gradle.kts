plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("xyz.jpenilla.run-paper") version "2.0.1"
    `maven-publish`
}

group = "com.owen1212055"

val nmsProject = project(":nms:nms-impl")
dependencies {
    // include all impls
    for (child in nmsProject.childProjects) {
        implementation(project(child.value.path, configuration = "reobf"))
    }

    implementation(project(":api"))
    implementation(project(":nms:nms-interface"))
}

publishing {
    publications {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }

        tasks.withType<GenerateModuleMetadata> {
            enabled = false
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/LemonGamingLtd/ParticleHelper")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    shadowJar {
        dependencies {
            relocate("org.bstats", "com.owen1212055.${rootProject.name}.libs.bstats")
        }

        archiveClassifier.set("")
        archiveBaseName.set("${parent?.project?.name}-${project.name}")
    }

    jar {
        archiveBaseName.set("${parent?.project?.name}-${project.name}")
    }

    assemble {
        dependsOn("shadowJar")
    }

    runServer {
        minecraftVersion("1.19.4")
    }

}