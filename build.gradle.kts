plugins {
    alias(libs.plugins.android.application) apply false  // No aplicar en el nivel del proyecto
    id("com.google.gms.google-services") version "4.4.2" apply false  // Tampoco aplicar aquí
}
