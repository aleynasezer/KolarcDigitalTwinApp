package com.kolarc.digitaltwin.presentation.notification

import com.kolarc.digitaltwin.domain.model.NotificationItem
import com.kolarc.digitaltwin.domain.model.NotificationSeverity

data class NotificationUiState(
    val isLoading: Boolean = true,
    val notifications: List<NotificationItem> = emptyList(),
    val filteredNotifications: List<NotificationItem> = emptyList(),
    val selectedSeverity: NotificationSeverity? = null,
    val errorMessage: String? = null
)