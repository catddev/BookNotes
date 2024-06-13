package com.epamupskills.booknotes.ui

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import com.epamupskills.booknotes.core.InputManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InputManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : InputManager {

    override fun hideKeyboard(windowToken: IBinder?, flags: Int) {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, flags)
    }
}