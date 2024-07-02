// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
dependencies {
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    implementation "androidx.compose.ui:ui:1.3.0"
    implementation "androidx.compose.material:material:1.3.0"
    implementation "com.github.PhilJay:MPAndroidChart:v3.1.0"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
}
