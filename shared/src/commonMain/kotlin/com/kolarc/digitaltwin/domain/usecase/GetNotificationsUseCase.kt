package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.domain.model.NotificationItem
import com.kolarc.digitaltwin.domain.repository.NotificationRepository

class GetNotificationsUseCase(
    private val repository: NotificationRepository
) {

    suspend operator fun invoke(): List<NotificationItem> {
        return repository.getNotifications()
    }
}