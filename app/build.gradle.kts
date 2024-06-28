plugins {
    alias(libs.plugins.androidApplication)//注意这里application library用的androidLibrary
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("xml-class-guard")
}
//apply(from ="../replaceMap.gradle")
//apply(from = "xmlClassGuard.gradle")
android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
//    signingConfigs {
//        create("sign") {
//            storeFile = File("../com.luckycat.pro.asd.jks")
//            storePassword = "123456"
//            keyAlias = "com.luckycat.pro.asd"
//            keyPassword = "123456"
////            enableV1Signing = true
////            enableV2Signing = true
////            enableV3Signing = true
////            enableV4Signing = true
//        }
//
//    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            signingConfigs.getAt("sign")
            buildConfigField("boolean", "LOG_DEBUG", "false")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            signingConfigs.getAt("sign")
            buildConfigField("boolean", "LOG_DEBUG", "true")
        }
    }
//    applicationVariants.all {
//        val variant = this
//        variant.outputs
//            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
//            .forEach { output ->
//                val outputFileName =
//                    "15 Pro max Camera - ${variant.baseName} - ${variant.versionName} - ${variant.baseName}.apk"
//                println("OutputFileName: $outputFileName")
//                output.outputFileName = outputFileName
//            }
//    }

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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        jniLibs {
            useLegacyPackaging = false//不压缩共享库 android15页面可使用16KB
        }
    }
}


dependencies {
    api(project(":mylibrary"))
    api(project(":NetLibrary"))
//#基础的库 start//
    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.compose.compiler.compiler)
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.foundation.foundation)
    implementation(libs.androidx.compose.material.material)
    implementation(libs.androidx.constraintlayout.constranintlayout.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.compose)
    implementation(libs.androidx.appcompat.appcompat)
    implementation(libs.androidx.appcompat.appcompat.resources)
    implementation(libs.com.google.android.material.material)
    implementation(libs.androidx.multidex.multidex)
//#基础的库end
    implementation(libs.androidx.compose.runtime.runtime.livedata)
    implementation(libs.com.google.accompanist.accompanist.insets)
    implementation(libs.com.google.accompanist.accompanist.systemuicontroller)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.androidx.core.core.splashscreen)
    implementation(libs.com.google.code.gson.gson)
    implementation(libs.com.tencent.mmkv)
    implementation(libs.com.squareup.okhttp3.okhttp)
    implementation(libs.com.github.skydoves.flexible.bottomsheet.material)
    //compose 动画库 目前只用于共享元素使用
    implementation(libs.androidx.compose.animation.animation)
    //compose navigation
    implementation(libs.androidx.navigation.navigation.compose)
    //计算库
    implementation(libs.net.objecthunter.exp4j)



}