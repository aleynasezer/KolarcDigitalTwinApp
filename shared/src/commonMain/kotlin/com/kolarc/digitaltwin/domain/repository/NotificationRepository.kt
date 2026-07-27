package com.kolarc.digitaltwin.domain.repository

import com.kolarc.digitaltwin.domain.model.NotificationItem

interface NotificationRepository {

    suspend fun getNotifications(): List<NotificationItem>
}