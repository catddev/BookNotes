package com.epamupskills.booknotes

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import com.epamupskills.core.AppRouter
import com.epamupskills.core.Navigate
import com.epamupskills.core.NavigateTo
import com.epamupskills.core.NavigateToGraph
import com.epamupskills.core.NavigateUp
import com.epamupskills.core.NavigationEvent
import com.epamupskills.core.Navigator
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class AppRouterImpl @Inject constructor(
    @ActivityContext private val context: Context,
) : AppRouter {

    private val navController by lazy { (context as Navigator).getRootNavController() }

    override fun navigateOnEvent(event: NavigationEvent, childNavController: NavController?) {
        (childNavController ?: navController).run {
            when (event) {
                is Navigate -> navigate(event.destinationId)
                is NavigateTo -> navigate(event.direction)
                is NavigateUp -> navigateUp()
                is NavigateToGraph -> setGraph(event.graphId)
                else -> Unit
            }
        }
    }

    override fun openMainActivity(context: Context, uri: Uri?) {
        val intent = Intent(context, MainActivity::class.java).apply {
            data = uri
        }
        context.startActivity(intent)
    }
}