package com.kolarc.digitaltwin.presentation.machine.detail

import com.kolarc.digitaltwin.domain.model.MachineDetail

data class MachineDetailUiState(
    val machineDetail: MachineDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)