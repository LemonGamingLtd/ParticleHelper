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