package com.epamupskills.core

import android.os.IBinder

interface InputManager {
    fun hideKeyboard(windowToken: IBinder?, flags: Int = 0)
}