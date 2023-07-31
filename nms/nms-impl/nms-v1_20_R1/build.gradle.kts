plugins {
    id("io.papermc.paperweight.userdev") version "1.5.5"
}

dependencies {
    implementation(project(":api"))
    implementation(project(":nms:nms-interface"))
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
}

publishing {
    publications {
        getByName<MavenPublication>("mavenJava") {
            // Stupid github doesn't allow numbers in artifacts?
            artifactId = providers.gradleProperty("name").get() + "twenty-one"
        }
    }
}
