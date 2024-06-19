package com.epamupskills.booknotes.core

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import com.epamupskills.booknotes.core.abstraction.AppRouter
import com.epamupskills.booknotes.core.abstraction.Navigator
import com.epamupskills.booknotes.ui.MainActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class AppRouterImpl @Inject constructor(
    @ActivityContext private val context: Context,
) : AppRouter {

    private val navController by lazy { (context as Navigator).getRootNavController() }

    override fun navigateOnEvent(
        event: NavigationEvent,
        currentNavController: NavController?,
        childNavController: NavController?
    ) {
        (childNavController ?: currentNavController ?: navController).run {
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