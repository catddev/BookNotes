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
-keep class **_HiltComponents { *; }
-keep class **_MembersInjector { *; }

#Hilt annotations
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponentManagerHolder
-keep class * extends dagger.hilt.components.SingletonComponent
-keep class * extends dagger.hilt.android.internal.modules.*
-keep @dagger.hilt.* class *
-keepclassmembers class * {
    @dagger.hilt.* *;
}
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel

# General rules for keeping metadata and reflection-relevant information
-keepattributes *Annotation*, Signature, EnclosingMethod, InnerClasses

-keep class com.epamupskills.core.** { *; }

-keep class com.epamupskills.core.ImageLoader { *; }
-keep class com.epamupskills.core.UserIntent { *; }
-keep class com.epamupskills.core.databinding.ViewErrorBinding
-keep class com.epamupskills.core.databinding.ViewLoaderBinding
-keep class com.epamupskills.core.databinding.ViewToolbarBinding
-keep class com.epamupskills.core.di.Glide
-keep class com.epamupskills.core.Navigate { *; }
-keep class com.epamupskills.core.NavigateTo { *; }
-keep class com.epamupskills.core.NavigateToGraph { *; }
-keep class com.epamupskills.core.NavigateUp{ *; }
-keep class com.epamupskills.core.NavigateWithConfig { *; }
-keep class com.epamupskills.core.NavigationEvent { *; }
-keep class com.epamupskills.core.base.BaseMapper { *; }