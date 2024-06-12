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

-dontwarn org.slf4j.impl.StaticLoggerBinder

#retrofit with coroutine adapter
-keep interface com.epamupskills.book_notes.data.api.GoogleBooksApi { *; }
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class retrofit2.CallAdapter
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

#Room database
-keep class androidx.room.RoomDatabase { *; }
-keep class * extends androidx.room.RoomDatabase { *; }

#DAO interfaces and methods
-keep @androidx.room.Dao class * { *; }

#Room annotations
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.PrimaryKey class * { *; }

#Room schema
-keep class **_Impl { *; }

#Kotlin metadata
-keep class kotlin.Metadata { *; }

#Firebase annotations
-keepattributes *Annotation*

#Firebase core
-keep class com.google.firebase.** { *; }

#Firebase Crashlytics
-keep class com.google.firebase.crashlytics.** { *; }
-keep class com.google.firebase.crashlytics.internal.model.** { *; }

#Hilt-Generated Components and Modules
-keep class dagger.hilt.internal.** { *; }
-keep class dagger.hilt.android.internal.** { *; }
-keep class dagger.hilt.android.internal.managers.** { *; }
-keep class dagger.hilt.android.internal.lifecycle.** { *; }
-keep class hilt_aggregated_deps.** { *; }

#Hilt annotations
-keepattributes *Annotation*
-keep class javax.inject.** { *; }
-keep class dagger.** { *; }
-keep class dagger.hilt.** { *; }
-keep class dagger.hilt.android.** { *; }

#Hilt components
-keep class **_HiltComponents { *; }
-keep class **_MembersInjector { *; }

# Hilt rules
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class dagger.** { *; }
-keep class hilt_aggregated_deps.** { *; }
-keep class androidx.hilt.** { *; }
-keep class hilt.** { *; }
-keep @dagger.hilt.codegen.OriginatingElement class ** { *; }
-keep @dagger.hilt.InstallIn class * {
    @dagger.hilt.InstallIn <fields>;
}
-keep @dagger.hilt.DefineComponent class * {
    @dagger.hilt.DefineComponent <fields>;
}
-keep @dagger.hilt.components.SingletonComponent class * {
    @dagger.hilt.components.SingletonComponent <fields>;
}

# General rules for keeping metadata and reflection-relevant information
-keepattributes *Annotation*, Signature, EnclosingMethod, InnerClasses