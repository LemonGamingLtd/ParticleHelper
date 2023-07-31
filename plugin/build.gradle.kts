plugins {
    java
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

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    jar {
        archiveBaseName.set("${parent?.project?.name}-${project.name}")
    }

    runServer {
        minecraftVersion("1.19.4")
    }

}