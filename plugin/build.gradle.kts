plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("xyz.jpenilla.run-paper") version "2.0.1"
    `maven-publish`
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