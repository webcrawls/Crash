plugins {
    kotlin("jvm") version "2.1.0-Beta2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "sh.kaden.crash"
version = "2.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") { name = "papermc-repo" }
    maven("https://oss.sonatype.org/content/groups/public/") { name = "sonatype" }
    maven("https://jitpack.io/") { name = "jitpack" }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("org.spongepowered:configurate-yaml:4.0.0")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    implementation("com.github.stefvanschie.inventoryframework:IF:0.10.17")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    relocate("com.github.stefvanschie.inventoryframework", "sh.kaden.crash.inventoryframework")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}
