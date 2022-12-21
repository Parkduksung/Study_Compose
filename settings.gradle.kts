pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}

rootProject.name = "StudyCompose"
include(":app")
include(":week1")
include(":week2")
include(":week3")
include(":week4")
include(":week5")
include(":week6")
include(":week7")
include(":week8")
include(":practice1")
include(":foundation")
include(":practice2")
include(":part1")
include(":practice3")
include(":book1")