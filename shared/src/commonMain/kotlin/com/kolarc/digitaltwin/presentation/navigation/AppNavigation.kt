package com.kolarc.digitaltwin.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kolarc.digitaltwin.presentation.MachineListScreen
import com.kolarc.digitaltwin.presentation.machine.detail.MachineDetailScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MachineListRoute
    ) {
        composable<MachineListRoute> {
            MachineListScreen(
                onMachineClick = { machineId ->
                    navController.navigate(
                        MachineDetailRoute(
                            machineId = machineId
                        )
                    )
                }
            )
        }

        composable<MachineDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<MachineDetailRoute>()

            MachineDetailScreen(
                machineId = route.machineId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}