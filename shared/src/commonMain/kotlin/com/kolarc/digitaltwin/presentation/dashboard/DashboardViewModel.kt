package com.kolarc.digitaltwin.presentation.dashboard

import com.kolarc.digitaltwin.domain.usecase.GetDashboardSummaryUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getDashboardSummaryUseCase: GetDashboardSummaryUseCase
) {

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    private val _uiState = MutableStateFlow(
        DashboardUiState()
    )

    val uiState: StateFlow<DashboardUiState> =
        _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    fun retry() {
        loadDashboard()
    }

    private fun loadDashboard() {

        _uiState.value = DashboardUiState(
            isLoading = true
        )

        scope.launch {

            runCatching {

                getDashboardSummaryUseCase()

            }.onSuccess { summary ->

                _uiState.value = DashboardUiState(
                    isLoading = false,
                    summary = summary
                )

            }.onFailure { throwable ->

                _uiState.value = DashboardUiState(
                    isLoading = false,
                    errorMessage = throwable.message
                        ?: "Unknown error"
                )

            }
        }
    }
}