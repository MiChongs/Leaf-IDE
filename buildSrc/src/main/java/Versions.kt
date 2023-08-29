import org.gradle.api.JavaVersion

object Versions {
    // Project configuration versions
    const val CompileSdkVersion = 34
    const val TargetSdkVersion = 28
    const val MinSdkVersion = 21
    const val VersionCode = 1
    const val VersionName = "1.0.0.0"

    // Project option versions
    val CompilingJavaVersion = JavaVersion.VERSION_11;
    const val JvmTarget = "11"
    const val AndroidGradlePluginVersion = "8.1.0"
    const val KotlinVersion = "1.8.10"
    const val KotlinCompilerExtensionVersion = "1.4.3"

    // Library versions
    const val CoreKtx = "1.10.1"
    const val LifecycleRuntimeKtx = "2.6.1"
    const val ActivityCompose = "1.7.2"
    const val ComposeBom = "2023.08.00"
    const val SplashScreen = "1.0.1"
    const val Navigation = "2.7.1"
    const val MaterialIconsExtended = "1.5.0"
    const val ConstraintLayout = "1.0.1"
    const val DataStorePreferences = "1.0.0"

    // The blur lib
    const val Cloudy = "0.1.2"
}