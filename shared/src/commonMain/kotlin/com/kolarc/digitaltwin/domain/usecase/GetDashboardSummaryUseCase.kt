package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.domain.model.DashboardSummary
import com.kolarc.digitaltwin.domain.repository.DashboardRepository

class GetDashboardSummaryUseCase(
    private val repository: DashboardRepository
) {

    suspend operator fun invoke(): DashboardSummary {
        return repository.getDashboardSummary()
    }
}