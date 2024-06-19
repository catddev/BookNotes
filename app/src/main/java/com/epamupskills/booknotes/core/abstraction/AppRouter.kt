package com.epamupskills.booknotes.core.abstraction

import android.content.Context
import android.net.Uri
import androidx.navigation.NavController
import com.epamupskills.booknotes.core.NavigationEvent

interface AppRouter {
    fun openMainActivity(context: Context, uri: Uri? = null)
    fun navigateOnEvent(
        event: NavigationEvent,
        currentNavController: NavController? = null,
        childNavController: NavController? = null
    )
}