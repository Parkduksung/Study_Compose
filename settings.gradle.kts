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
include(":book2")
include(":swipetorefresh")
include(":paging")
include(":stringtoimage")
include(":dialog")
include(":viewpager")
include(":lectures")
include(":ppt")
include(":swipedismiss")
include(":gif")
include(":parcelable")
include(":piechart")
include(":bottomsheet")
include(":basetextfield")
include(":flowlayout")
include(":maxlengthtextfiled")
include(":navigateitem")
include(":tooltip")
include(":paging3")
include(":canvas")
include(":keyboard")
include(":hangeul")
include(":timedistance")
include(":collapsingtoolbar")
