import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "fi.hsl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://dl.bintray.com/hsldevcom/maven")
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveFileName.set("${baseName}.${extension}")
    }
}

val jar by tasks.getting(Jar::class){
    manifest{
        attributes["Main-Class"] = "fi.hsl.ekecheck.ApplicationKt"
        attributes["Implementation-Version"] = project.version
    }
}


dependencies {
    testImplementation(kotlin("test-junit"))
    implementation ("fi.hsl:transitdata-common:1.3.19")
    implementation ("io.github.microutils:kotlin-logging:1.6.22")
    implementation ("io.ktor:ktor:1.5.1")
    //implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation("io.ktor:ktor-server-netty:1.5.1")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}