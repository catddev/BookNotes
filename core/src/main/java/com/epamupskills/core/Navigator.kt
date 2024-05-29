package com.epamupskills.core

import androidx.navigation.NavController

interface Navigator {
    fun getRootNavController(): NavController
}