plugins {
    alias(libs.plugins.androidLibrary)//注意这里library app用的application
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.ethan.netlibrary"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        //依赖库里targetSdk这么写
        testOptions.targetSdk = 33
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//            signingConfigs.getAt("sign")
//            buildConfigField("boolean", "LOG_DEBUG", "true")
        }
    }

//    lint {
//        abortOnError = false
//        checkReleaseBuilds = false
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
//        compose = true
        buildConfig = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.11"
//    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
}


dependencies {
    implementation(libs.com.squareup.okhttp3.okhttp)
    implementation(libs.com.tencent.mmkv)
    implementation(libs.com.google.code.gson.gson)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.lifecycle.livedata.ktx)

}