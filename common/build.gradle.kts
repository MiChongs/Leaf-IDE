plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.github.caimucheng.leaf.common"
    compileSdk = Versions.CompileSdkVersion

    defaultConfig {
        minSdk = Versions.MinSdkVersion
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = Versions.CompilingJavaVersion
        targetCompatibility = Versions.CompilingJavaVersion
    }
    kotlinOptions {
        jvmTarget = Versions.JvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.KotlinCompilerExtensionVersion
    }
}

dependencies {
    implementation("androidx.constraintlayout:constraintlayout-compose:${Versions.ConstraintLayout}")
    implementation("androidx.compose.material:material-icons-extended:${Versions.MaterialIconsExtended}")
    implementation("androidx.datastore:datastore-preferences:${Versions.DataStorePreferences}")
    implementation("androidx.core:core-ktx:${Versions.CoreKtx}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LifecycleRuntimeKtx}")
    implementation("androidx.activity:activity-compose:${Versions.ActivityCompose}")
    implementation(platform("androidx.compose:compose-bom:${Versions.ComposeBom}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
}