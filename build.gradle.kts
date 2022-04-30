import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    application
}

buildscript {
    val kotlinVersion = "1.6.21"
    val springBootVersion = "2.6.7"
    val dependencyManagementVersion = "1.0.11.RELEASE"

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("org.jetbrains.kotlin:kotlin-allopen:${kotlinVersion}")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        classpath("io.spring.gradle:dependency-management-plugin:${dependencyManagementVersion}")
    }

}

subprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-spring")

    dependencies {
        // JSR 303: Bean validation
        implementation("javax.validation:validation-api:2.0.1.Final")

        // JSR 310: Jackson For Java 8
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.2")

        // Kotlin
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
    }

    tasks.withType<KotlinCompile>() {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project(":server") {
    dependencies {

    }
}

project(":client") {
    dependencies {

    }
}

project(":core") {
    dependencies {

    }
}