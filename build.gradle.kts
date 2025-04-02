plugins {
    alias(libs.plugins.android.application) apply false  // No aplicar en el nivel del proyecto
    id("com.google.gms.google-services") version "4.4.2" apply false  // Tampoco aplicar aquí
    id("com.google.dagger.hilt.android") version "2.48" apply false
}

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("com.google.gms:google-services:4.4.1")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
