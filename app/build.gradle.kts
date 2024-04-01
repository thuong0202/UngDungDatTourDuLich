plugins {
    id("com.android.application")
}

android {
    namespace = "huflit.phn.dulichvietnam"
    compileSdk = 34

    defaultConfig {
        applicationId = "huflit.phn.dulichvietnam"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation ("com.google.android.material:material:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    implementation("com.android.car.ui:car-ui-lib:2.6.0")
    testImplementation("junit:junit:4.13.2")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.android.support:multidex:1.0.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("com.github.bumptech.glide:annotations:4.13.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.github.bumptech.glide:glide:4.13.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")


}