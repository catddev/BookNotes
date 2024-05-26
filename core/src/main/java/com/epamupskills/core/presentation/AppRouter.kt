package com.epamupskills.core.presentation

import android.content.Context
import android.net.Uri

interface AppRouter {
    fun openMainActivity(context: Context, uri: Uri? = null)
}