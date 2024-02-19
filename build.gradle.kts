plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.2.3"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenLocal() // hephaestus-engine (dev)
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") // mocha
    maven("https://papermc.io/repo/repository/maven-public/") // paper-api
    maven("https://maven.citizensnpcs.co/repo") // Citizens
    maven("https://repo.unnamed.team/repository/unnamed-public/") // command-flow
    maven("https://mvn.lumine.io/repository/maven-public/") // MythicMobs
    mavenCentral() // creative-central, hephaestus-engine
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT") // paper-api

    val hephaestusVersion = "0.9.0-dev-SNAPSHOT"
    implementation("team.unnamed:hephaestus-api:$hephaestusVersion") // hephaestus
    implementation("team.unnamed:hephaestus-reader-blockbench:$hephaestusVersion") // hephaestus
    implementation("team.unnamed:hephaestus-runtime-bukkit-api:$hephaestusVersion") // hephaestus
    implementation(files("lib/hephaestus-runtime-bukkit-adapt-v1_20_R3-$hephaestusVersion-reobf.jar")) // hephaestus
    implementation("me.fixeddev:commandflow-universal:0.6.0") // command-flow
    implementation("me.fixeddev:commandflow-bukkit:0.6.0") // command-flow

    compileOnly("team.unnamed:creative-central-api:1.3.0") // creative-central
    compileOnly("io.lumine:Mythic-Dist:5.3.5") // MythicMobs
    compileOnly("net.citizensnpcs:citizens-main:2.0.30-SNAPSHOT") { // Citizens
        exclude(group = "*", module = "*")
    }
}

tasks {
    runServer {
        downloadPlugins {
            modrinth("central", "1.3.0") // creative-central
            url("https://ci.citizensnpcs.co/job/citizens2/lastSuccessfulBuild/artifact/dist/target/Citizens-2.0.33-b3320.jar") // Citizens
        }

        minecraftVersion("1.20.4")
    }
    processResources {
        filesMatching("plugin.yml") {
            expand("project" to project)
        }
    }
    shadowJar {
        val pkg = "team.unnamed.hephaestuser.lib"
        relocate("me.fixeddev.commandflow", "$pkg.commandflow")

        dependencies {
            // all these dependencies are provided by the server
            exclude(dependency("team.unnamed:creative-api"))
            exclude(dependency("net.kyori:adventure-.*"))
            exclude(dependency("net.kyori:examination-.*"))
            exclude(dependency("com.google.code.gson:gson"))
            exclude(dependency("org.jetbrains:annotations"))
        }
    }
}