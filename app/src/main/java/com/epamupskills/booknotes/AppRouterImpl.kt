package com.epamupskills.booknotes

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.epamupskills.core.presentation.AppRouter
import javax.inject.Inject

class AppRouterImpl @Inject constructor() : AppRouter {

    override fun openMainActivity(context: Context, uri: Uri?) {
        val intent = Intent(context, MainActivity::class.java).apply {
            data = uri
        }
        context.startActivity(intent)
    }
}