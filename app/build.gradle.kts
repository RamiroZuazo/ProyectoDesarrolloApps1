plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ProyectoDesarrolloDeApps1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ProyectoDesarrolloDeApps1"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:<versión>'
    -----------------------------------------------------------------
    // Convertidor JSON (por ejemplo, Moshi o Gson)
    implementation 'com.squareup.retrofit2:converter-gson:<versión>'
    -----------------------------------------------------------------
    // OkHttp
    implementation 'com.squareup.okhttp3:okhttp:<versión>'
    implementation 'com.squareup.okhttp3:logging-interceptor:<versión>'
    -----------------------------------------------------------------
    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    annotationProcessor("com.google.dagger:hilt-android-compiler:2.48")
    -----------------------------------------------------------------
    // Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    -----------------------------------------------------------------

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}