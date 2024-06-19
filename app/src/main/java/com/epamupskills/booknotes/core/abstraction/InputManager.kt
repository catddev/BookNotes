package com.epamupskills.booknotes.core.abstraction

import android.os.IBinder

interface InputManager {
    fun hideKeyboard(windowToken: IBinder?, flags: Int = 0)
}