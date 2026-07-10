package com.kolarc.digitaltwin.presentation.machine

import com.kolarc.digitaltwin.domain.model.MachineStatus

data class MachineListUiState(
    val machines: List<MachineStatus> = emptyList(),
    val selectedTab: Int = OVERVIEW_TAB_INDEX,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val filteredMachines: List<MachineStatus>
        get() {
            if (searchQuery.isBlank()) {
                return machines
            }

            return machines.filter { machine ->
                machine.name.contains(
                    other = searchQuery,
                    ignoreCase = true
                )
            }
        }

    companion object {
        const val OVERVIEW_TAB_INDEX = 0
        const val LIVE_VIEW_TAB_INDEX = 1
    }
}