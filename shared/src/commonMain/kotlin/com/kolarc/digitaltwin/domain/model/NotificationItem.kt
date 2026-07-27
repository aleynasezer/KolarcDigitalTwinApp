package com.kolarc.digitaltwin.domain.model

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val severity: NotificationSeverity,
    val timestamp: String,
    val isRead: Boolean
)

enum class NotificationSeverity {
    INFO,
    WARNING,
    CRITICAL
}