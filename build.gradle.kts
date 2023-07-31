plugins {
    java
    id("maven-publish")
}

group = "ltd.lemongaming"
version = "1.5.0"

subprojects {
    apply(plugin = "java")
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
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            evaluationDependsOn(":api")
            evaluationDependsOn(":nms")

            groupId = group as String
            artifactId = "particlehelper"
            from(project(":plugin").components["java"])
        }

        tasks.withType<GenerateModuleMetadata> {
            enabled = false
        }
    }

    repositories {
        maven {
            name = "bytecodespace"

            val releasesRepoUrl = uri("https://repo.bytecode.space/repository/maven-releases/")
            val snapshotsRepoUrl = uri("https://repo.bytecode.space/repository/maven-snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            credentials(PasswordCredentials::class)
        }
    }
}