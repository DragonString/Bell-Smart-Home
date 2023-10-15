rootProject.name = "BellSmartHome"

dependencyResolutionManagement {
    versionCatalogs {
        create("paketobuildpacks") {
            from(files("./gradle/paketobuildpacks.versions.toml"))
        }
    }
}
