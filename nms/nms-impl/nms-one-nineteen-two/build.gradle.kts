// Github Packages doesn't allow for numbers in artifact ids, good job guys! Now we need stupid one-dot-twenty-one names!
plugins {
    id("io.papermc.paperweight.userdev") version "1.5.5"
}

dependencies {
    implementation(project(":api"))
    implementation(project(":nms:nms-interface"))
    paperweight.paperDevBundle("1.19.2-R0.1-SNAPSHOT")
}
