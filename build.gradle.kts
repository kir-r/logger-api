import java.net.*

plugins {
    kotlin("multiplatform")
    id("com.github.hierynomus.license")
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

val scriptUrl: String by extra

apply(from = "$scriptUrl/git-version.gradle.kts")
apply(from = "$scriptUrl/maven-repo.gradle.kts")

repositories {
    mavenLocal()
    mavenCentral()
}

kotlin {
    targets {
        mingwX64()
        macosX64()
        linuxX64()
        jvm {
            val test by compilations
            test.defaultSourceSet {
                dependencies {
                    implementation(kotlin("test-junit"))
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(System.getenv("MAVEN_USERNAME"))
            password.set(System.getenv("MAVEN_PASSWORD"))
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
    useInMemoryPgpKeys(System.getenv("GPG_SIGNING_KEY"), System.getenv("GPG_PASSPHRASE"))
}

val licenseFormatSettings by tasks.registering(com.hierynomus.gradle.license.tasks.LicenseFormat::class) {
    source = fileTree(project.projectDir).also {
        include("**/*.kt", "**/*.java", "**/*.groovy")
        exclude("**/.idea")
    }.asFileTree
    headerURI = URI("https://raw.githubusercontent.com/Drill4J/drill4j/develop/COPYRIGHT")
}

tasks["licenseFormat"].dependsOn(licenseFormatSettings)
