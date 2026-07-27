package com.kolarc.digitaltwin.domain.repository

import com.kolarc.digitaltwin.domain.model.DashboardSummary

interface DashboardRepository {

    suspend fun getDashboardSummary(): DashboardSummary
}