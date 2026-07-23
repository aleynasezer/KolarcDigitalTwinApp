package com.kolarc.digitaltwin.presentation.machine.detail

import com.kolarc.digitaltwin.domain.usecase.GetMachineDetailUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MachineDetailViewModel(
    private val machineId: String,
    private val getMachineDetailUseCase: GetMachineDetailUseCase
) {

    private val viewModelScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    private val _uiState = MutableStateFlow(
        MachineDetailUiState(
            isLoading = true
        )
    )

    val uiState: StateFlow<MachineDetailUiState> =
        _uiState.asStateFlow()

    init {
        loadMachineDetail()
    }

    private fun loadMachineDetail() {
        viewModelScope.launch {
            try {
                val machineDetail = getMachineDetailUseCase(machineId)

                _uiState.value = MachineDetailUiState(
                    machineDetail = machineDetail,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (exception: Exception) {
                _uiState.value = MachineDetailUiState(
                    machineDetail = null,
                    isLoading = false,
                    errorMessage = exception.message
                )
            }
        }
    }
}