package com.epamupskills.booknotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.epamupskills.booknotes.ui.theme.BookNotesTheme
import com.epamupskills.core.presentation.AppRouter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    @Inject
    lateinit var router: AppRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            BookNotesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SplashScreen(
                        animation = R.raw.lottie_splash_animation,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        router.openMainActivity(this)
                        finishAndRemoveTask() //todo Z-2 !!!
                    }
                }
//            }
        }
    }
}

@Composable
fun SplashScreen(
    @RawRes animation: Int,
    modifier: Modifier = Modifier,
    block: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(animation))
        val logoAnimationState = animateLottieCompositionAsState(composition = composition)
        LottieAnimation(composition = composition, progress = { logoAnimationState.progress })
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) block.invoke()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    BookNotesTheme {
        SplashScreen(animation = R.raw.lottie_splash_animation, block = {})
    }
}