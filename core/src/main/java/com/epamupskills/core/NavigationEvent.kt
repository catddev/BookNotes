package com.epamupskills.core

import androidx.navigation.NavDirections

sealed class NavigationEvent
data class Navigate(val destinationId: Int, val isRoot: Boolean = false) : NavigationEvent()
data class NavigateTo(val direction: NavDirections, val isRoot: Boolean = false) : NavigationEvent()
data class NavigateToGraph(val graphId: Int) : NavigationEvent()
data class NavigateUp(val isRoot: Boolean = false) : NavigationEvent()
data class NavigateWithNestedNavHost(
    val navHostId: Int,
    val direction: NavDirections
) : NavigationEvent()