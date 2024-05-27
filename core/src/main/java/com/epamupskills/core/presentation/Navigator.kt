package com.epamupskills.core.presentation

import androidx.navigation.NavController

interface Navigator {
    fun getRootNavController(): NavController
}