package com.epamupskills.booknotes.core.abstraction

import androidx.navigation.NavController

interface Navigator {
    fun getRootNavController(): NavController
}