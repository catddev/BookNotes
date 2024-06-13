# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontobfuscate

-dontwarn org.slf4j.impl.StaticLoggerBinder

#retrofit with coroutine adapter
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class retrofit2.CallAdapter
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.CoroutineContext

#Room database
-keep class androidx.room.RoomDatabase  { *; }
-keep class * extends androidx.room.RoomDatabase { *; }

#DAO interfaces and methods
-keep @androidx.room.Dao class * { *; }

#Room annotations
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.PrimaryKey class * { *; }

#Room schema
-keep class **_Impl { *; }

#Kotlin metadata
-keep class kotlin.Metadata { *;}

#Firebase annotations
-keepattributes *Annotation*

#Firebase core
-keep class com.google.firebase.** { *; }

#Firebase Authentication
-keep class com.google.firebase.auth.** { *; }

#Firebase Crashlytics
-keep class com.google.firebase.crashlytics.** { *; }
-keep class com.google.firebase.crashlytics.internal.model.** { *; }

-dontwarn org.bouncycastle.**
-dontwarn org.conscrypt.**
-dontwarn org.openjsse.**
-dontwarn com.google.auto.value.**