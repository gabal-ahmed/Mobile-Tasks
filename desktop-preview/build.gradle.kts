plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
}

kotlin { jvmToolchain(21) }

sourceSets["main"].kotlin.srcDir("../app/src/main/java/com/example/drawer/ui")

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    testImplementation(compose.desktop.uiTestJUnit4)
    testImplementation(kotlin("test"))
}

tasks.test {
    systemProperty("screenshots.dir", rootProject.file("screenshots").absolutePath)
}
