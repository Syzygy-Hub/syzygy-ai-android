plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.21"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2"
    id("maven-publish")
}

// Single canonical version source — bump only this value on each release.
val syzygyVersion = "1.1.0"

group = "com.github.Syzygy-Hub"
version = syzygyVersion

kotlin {
    jvmToolchain(17)
}

// ---------------------------------------------------------------------------
// Source sets
// ---------------------------------------------------------------------------

sourceSets {
    main {
        kotlin.srcDirs("src/main/kotlin")
    }
}

dependencies {
    // Foundation layer — all AI contracts depend on Foundation primitives.
    api("com.github.Syzygy-Hub:syzygy-foundation-android:1.2.0")

    // Coroutines — required for suspend functions and Flow in LLMProvider.
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    // Unit tests — JUnit 5 (Jupiter) via the Kotlin test wrapper.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:2.0.21")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// ---------------------------------------------------------------------------
// Publishing — JitPack
// ---------------------------------------------------------------------------

val mainSourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["java"])
            groupId = "com.github.Syzygy-Hub"
            artifactId = "syzygy-ai-android"
            version = syzygyVersion
            artifact(mainSourcesJar)
        }
        create<MavenPublication>("releaseWithSources") {
            groupId = "com.github.Syzygy-Hub"
            artifactId = "syzygy-ai-android-sources"
            version = syzygyVersion
            artifact(mainSourcesJar)
        }
    }
}

// Use JUnit Platform (JUnit 5) as the test engine.
tasks.withType<Test> {
    useJUnitPlatform()
}

// ---------------------------------------------------------------------------
// ktlint — lint main Kotlin sources directly via ktlint-cli
// ---------------------------------------------------------------------------

val ktlintCli: Configuration by configurations.creating

dependencies {
    ktlintCli("com.pinterest.ktlint:ktlint-cli:1.0.1")
}

val ktlintCheckSources by tasks.registering(JavaExec::class) {
    group = "verification"
    description = "Runs ktlint against src/main/**/*.kt"
    classpath = ktlintCli
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("src/main/**/*.kt")
    workingDir = project.projectDir
}

tasks.named("ktlintCheck") {
    dependsOn(ktlintCheckSources)
}

val ktlintFormatSources by tasks.registering(JavaExec::class) {
    group = "formatting"
    description = "Auto-fixes ktlint violations in src/main/**/*.kt"
    classpath = ktlintCli
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "src/main/**/*.kt")
    workingDir = project.projectDir
}

tasks.named("ktlintFormat") {
    dependsOn(ktlintFormatSources)
}
