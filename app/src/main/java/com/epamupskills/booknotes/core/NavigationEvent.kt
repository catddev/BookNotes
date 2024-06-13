package com.epamupskills.booknotes.core

import androidx.navigation.NavDirections

sealed class NavigationEvent(
    open val usesRootNavController: Boolean = false,
    open val usesChildNavController: Boolean = false,
    open val navHostId: Int = 0,
)

data class NavigateUp(
    override val usesChildNavController: Boolean = false,
    override val navHostId: Int = 0,
) : NavigationEvent(usesChildNavController =usesChildNavController, navHostId = navHostId)

data class Navigate(val destinationId: Int) : NavigationEvent()

data class NavigateToGraph(val graphId: Int) : NavigationEvent()

data class NavigateTo(
    val direction: NavDirections,
    override val usesChildNavController: Boolean = false,
    override val navHostId: Int = 0,
) : NavigationEvent(usesChildNavController =usesChildNavController, navHostId = navHostId)

data class NavigateWithConfig(
    val configBoolRes: Int = 0,
    val onConfigTrueNavEvent: NavigationEvent,
    val onConfigFalseNavEvent: NavigationEvent,
) : NavigationEvent()