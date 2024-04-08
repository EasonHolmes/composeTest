buildscript {
//    extra["kotlin_version"] = "1.8.0"
//    extra["lifecycle"] = "2.6.2"
//    extra["appcompant"] = "1.6.1"
//    extra["material"] = "1.10.0"
//    extra["compose_version"] = "1.5.3"
//    extra["compose_foundation"] = "1.5.3"
//    extra["compose_material"] = "1.5.3"
//    extra["constraintlayout_version"] = "2.1.1"
    repositories {
        maven(url = "https://jitpack.io")
    }

    dependencies {
        //add 2 items below
//        classpath "com.aliyun.build.android:product:1.0.0"
//        classpath "com.github.megatronking.stringfog:gradle-plugin:3.0.0"
        classpath("com.github.liujingxing:XmlClassGuard:1.2.6")
//        classpath("com.github.coolxinxin:ClassResGuard:1.0.7")
//        classpath "com.github.megatronking.stringfog:gradle-plugin:5.0.0"
        // 选用加解密算法库，默认实现了xor算法，也可以使用自己的加解密库。
//        classpath "com.github.megatronking.stringfog:xor:5.0.0"
    }

}


// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}
//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
