plugins { id("com.android.application") }

// Naslov spletne aplikacije in številko različice poda GitHub Actions.
val appUrl = (project.findProperty("appUrl") as String?) ?: "https://ludamus.si/"
val vCode = ((project.findProperty("versionCode") as String?) ?: "1").toInt()

android {
    namespace = "si.angleskebesede.app"
    compileSdk = 34
    defaultConfig {
        applicationId = "si.ludamus.app"
        minSdk = 24
        targetSdk = 34
        versionCode = vCode
        versionName = "1.$vCode"
        buildConfigField("String", "APP_URL", "\"$appUrl\"")
    }
    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KS_FILE") ?: "release.jks")
            storePassword = System.getenv("KS_PASS")
            keyAlias = "besede"
            keyPassword = System.getenv("KS_PASS")
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
    buildFeatures { buildConfig = true }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
