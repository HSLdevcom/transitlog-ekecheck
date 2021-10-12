plugins {
    kotlin("jvm") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "fi.hsl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven{
        name = "GitHub transitdata-common"
        url = uri("https://maven.pkg.github.com/hsldevcom/*")
        credentials  {
            username = (project.findProperty("github.user") ?: System.getenv("GITHUB_ACTOR")).toString()
            password = (project.findProperty("github.token") ?: System.getenv("GITHUB_TOKEN")).toString()
        }
    }
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
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.5.21")

    implementation ("fi.hsl:transitdata-common:1.3.25")

    implementation ("io.github.microutils:kotlin-logging:2.0.11")

    implementation ("io.ktor:ktor:1.6.3")
    implementation("io.ktor:ktor-server-netty:1.6.4")

    implementation("org.json:json:20210307")

    //implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
}

tasks.test {
    useJUnit()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}