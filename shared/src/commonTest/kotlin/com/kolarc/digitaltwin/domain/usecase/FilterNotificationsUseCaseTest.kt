package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.domain.model.NotificationItem
import com.kolarc.digitaltwin.domain.model.NotificationSeverity
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterNotificationsUseCaseTest {

    @Test
    fun selectedSeverityCriticalOldugundaSadeceCriticalBildirimlerDoner() {
        val notifications = listOf(
            NotificationItem(
                id = "1",
                title = "Makine Çevrimdışı",
                message = "Bağlantı kesildi.",
                severity = NotificationSeverity.CRITICAL,
                timestamp = "09:12",
                isRead = false
            ),
            NotificationItem(
                id = "2",
                title = "Bakım Yaklaşıyor",
                message = "Bakım zamanı yaklaşıyor.",
                severity = NotificationSeverity.WARNING,
                timestamp = "08:45",
                isRead = false
            ),
            NotificationItem(
                id = "3",
                title = "Enerji Raporu",
                message = "Rapor hazırlandı.",
                severity = NotificationSeverity.INFO,
                timestamp = "08:00",
                isRead = true
            )
        )

        val useCase = FilterNotificationsUseCase()

        val result = useCase(
            notifications = notifications,
            selectedSeverity = NotificationSeverity.CRITICAL
        )

        assertEquals(
            expected = 1,
            actual = result.size
        )

        assertEquals(
            expected = NotificationSeverity.CRITICAL,
            actual = result.first().severity
        )
    }

    @Test
    fun selectedSeverityNullOldugundaTumBildirimlerDoner() {
        val notifications = listOf(
            NotificationItem(
                id = "1",
                title = "Critical",
                message = "Critical message",
                severity = NotificationSeverity.CRITICAL,
                timestamp = "09:12",
                isRead = false
            ),
            NotificationItem(
                id = "2",
                title = "Warning",
                message = "Warning message",
                severity = NotificationSeverity.WARNING,
                timestamp = "08:45",
                isRead = false
            )
        )

        val useCase = FilterNotificationsUseCase()

        val result = useCase(
            notifications = notifications,
            selectedSeverity = null
        )

        assertEquals(
            expected = 2,
            actual = result.size
        )
    }
}