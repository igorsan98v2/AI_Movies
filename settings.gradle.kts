pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AIMovies"
include(":app")
include(":domain")
include(":data")
include(":common")
include(":feature-movies-list")
include(":feature-movie-details")
include(":utils")
