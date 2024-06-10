package com.epamupskills.core

import androidx.navigation.NavDirections

sealed class NavigationEvent(
    open val needChildNavController: Boolean = false,
    open val navHostId: Int = 0,
)

data object NavigateUp : NavigationEvent()

data class Navigate(val destinationId: Int) : NavigationEvent()

data class NavigateToGraph(val graphId: Int) : NavigationEvent()

data class NavigateTo(
    val direction: NavDirections,
    override val needChildNavController: Boolean = false,
    override val navHostId: Int = 0,
) : NavigationEvent(needChildNavController, navHostId)

data class NavigateWithConfig(
    val configBoolRes: Int = 0,
    val onConfigTrueNavEvent: NavigationEvent,
    val onConfigFalseNavEvent: NavigationEvent,
) : NavigationEvent()