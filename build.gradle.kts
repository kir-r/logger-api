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

java.sourceSets.create("src/commonMain/kotlin")

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            pom {
                name.set("logger-api")
                description.set("GitHub Actions Maven Central Test")
                url.set("hhttps://github.com/Drill4J/logger-api")

                scm {
                    connection.set("scm:git:https://github.com/Drill4J/logger-api.git")
                    developerConnection.set("scm:git:git@github.com:Drill4J/logger-api.git")
                    url.set("https://github.com/Drill4J/logger-api")
                }

                licenses {
                    license {
                        name.set("Apache 2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }

                developers {
                    developer {
                        id.set("Drill4J")
                        name.set("Drill4J")
                        email.set("drill4j@gmail.com")
                        url.set("https://drill4j.github.io/")
                    }
                }
            }

            from(components["java"])
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
