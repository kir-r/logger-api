rootProject.name = "logger-api"

val scriptUrl: String by extra
apply(from = "$scriptUrl/maven-repo.settings.gradle.kts")

pluginManagement {
    val kotlinVersion: String by extra
    plugins {
        kotlin("multiplatform") version kotlinVersion
    }
}
