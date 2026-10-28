pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "mobilePrograming"
include(":app")
include(":mp0301")
include(":mp0302")
include(":mp0303")
include(":mp0304")
include(":mp0305")
include(":mp0306")
include(":mp0401")
include(":mp0402")
include(":mp0403")
include(":mp0404")
include(":mp0501")
include(":mp0502")
include(":mp0503")
include(":mp0504")
include(":mp0505")
include(":mp0601")
include(":mp0602")
include(":mp0603")
include(":mp0604")
include(":mp0605")
include(":mp0701")
include(":mp0702")
include(":mp0703")
include(":mp0704")
include(":mp0705")