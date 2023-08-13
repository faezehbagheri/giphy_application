plugins {
    id(Plugins.kotlinLibrary)
}

dependencies {
    implementation(project(":libraries:common"))
    implementation(Libs.JavaX.inject)
    implementation(Libs.Pagination.pagingCommon)
    testImplementation(Libs.Test.junit)
}
