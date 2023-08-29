import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
var keystoreProperties: Properties? = null
if (keystorePropertiesFile.isFile && keystorePropertiesFile.exists()) {
    keystoreProperties = Properties()
    FileInputStream(keystorePropertiesFile).use {
        keystoreProperties!!.load(it)
    }
}

android {
    namespace = "io.github.caimucheng.leaf.ide"
    compileSdk = Versions.CompileSdkVersion

    defaultConfig {
        applicationId = "io.github.caimucheng.leaf.ide"
        minSdk = Versions.MinSdkVersion
        //noinspection ExpiredTargetSdkVersion
        targetSdk = Versions.TargetSdkVersion
        versionCode = Versions.VersionCode
        versionName = Versions.VersionName
    }

    signingConfigs {
        if (keystorePropertiesFile.isFile && keystorePropertiesFile.exists()) {
            create("shared") {
                storePassword = keystoreProperties!!.getProperty("storePassword")
                keyAlias = keystoreProperties!!.getProperty("keyAlias")
                keyPassword = keystoreProperties!!.getProperty("keyPassword")
                storeFile = rootProject.file("buildKey.jks")
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            if (keystorePropertiesFile.isFile && keystorePropertiesFile.exists()) {
                signingConfig = signingConfigs.getByName("shared")
            }
        }
        release {
            isMinifyEnabled = false
            if (keystorePropertiesFile.isFile && keystorePropertiesFile.exists()) {
                signingConfig = signingConfigs.getByName("shared")
            }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.core:core-splashscreen:${Versions.SplashScreen}")
    implementation("androidx.compose.material:material-icons-extended:${Versions.MaterialIconsExtended}")
    implementation("androidx.constraintlayout:constraintlayout-compose:${Versions.ConstraintLayout}")
    implementation("androidx.datastore:datastore-preferences:${Versions.DataStorePreferences}")
    //noinspection GradleDependency
    implementation("androidx.core:core-ktx:${Versions.CoreKtx}")
    implementation("androidx.navigation:navigation-compose:${Versions.Navigation}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LifecycleRuntimeKtx}")
    implementation("androidx.activity:activity-compose:${Versions.ActivityCompose}")
    implementation(platform("androidx.compose:compose-bom:${Versions.ComposeBom}"))
    implementation("com.github.skydoves:cloudy:${Versions.Cloudy}")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
}