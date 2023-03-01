rootProject.name = "document-store"

pluginManagement {
    plugins {
        id("org.eazyportal.plugin.portal") version(extra["eazyPortalPluginVersion"] as String)
    }

    repositories {
        gradlePluginPortal()

        maven {
            name = "github"
            url = uri("${extra["githubUrl"] as String}/*")

            credentials {
                password = extra["githubPassword"] as String
                username = extra["githubUsername"] as String
            }
        }
    }
}

listOf(
    "api",
    "behemoth",
    "client",
    "dao",
    "service",
    "web",
    "ui"
).forEach {
    include(it)
    project(":$it").name = "${rootProject.name}-$it"
}
