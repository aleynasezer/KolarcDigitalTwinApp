package com.kolarc.digitaltwin.presentation.dashboard

import com.kolarc.digitaltwin.domain.model.DashboardSummary

data class DashboardUiState(
    val isLoading: Boolean = true,
    val summary: DashboardSummary? = null,
    val errorMessage: String? = null
)