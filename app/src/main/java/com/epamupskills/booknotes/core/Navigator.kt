package com.epamupskills.booknotes.core

import androidx.navigation.NavController

interface Navigator {
    fun getRootNavController(): NavController
}