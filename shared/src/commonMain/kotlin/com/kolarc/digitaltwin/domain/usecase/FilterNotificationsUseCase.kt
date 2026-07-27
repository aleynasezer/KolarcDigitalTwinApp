package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.domain.model.NotificationItem
import com.kolarc.digitaltwin.domain.model.NotificationSeverity

class FilterNotificationsUseCase {

    operator fun invoke(
        notifications: List<NotificationItem>,
        selectedSeverity: NotificationSeverity?
    ): List<NotificationItem> {
        return if (selectedSeverity == null) {
            notifications
        } else {
            notifications.filter { notification ->
                notification.severity == selectedSeverity
            }
        }
    }
}