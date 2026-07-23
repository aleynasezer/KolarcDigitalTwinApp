package com.kolarc.digitaltwin.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object MachineListRoute

@Serializable
data class MachineDetailRoute(
    val machineId: String
)