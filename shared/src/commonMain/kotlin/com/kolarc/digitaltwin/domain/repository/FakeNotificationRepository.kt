package com.kolarc.digitaltwin.data.repository

import com.kolarc.digitaltwin.domain.model.NotificationItem
import com.kolarc.digitaltwin.domain.model.NotificationSeverity
import com.kolarc.digitaltwin.domain.repository.NotificationRepository
import kotlinx.coroutines.delay

class FakeNotificationRepository : NotificationRepository {

    override suspend fun getNotifications(): List<NotificationItem> {

        delay(400)

        return listOf(

            NotificationItem(
                id = "1",
                title = "Makine Çevrimdışı",
                message = "PRES KAYNAGI bağlantısını kaybetti.",
                severity = NotificationSeverity.CRITICAL,
                timestamp = "09:12",
                isRead = false
            ),

            NotificationItem(
                id = "2",
                title = "Bakım Yaklaşıyor",
                message = "Utest-3 için bakım zamanı yaklaşıyor.",
                severity = NotificationSeverity.WARNING,
                timestamp = "08:45",
                isRead = false
            ),

            NotificationItem(
                id = "3",
                title = "Enerji Raporu",
                message = "Günlük enerji raporu oluşturuldu.",
                severity = NotificationSeverity.INFO,
                timestamp = "08:00",
                isRead = true
            ),

            NotificationItem(
                id = "4",
                title = "Alarm Temizlendi",
                message = "Hol-1 alarmı başarıyla kapatıldı.",
                severity = NotificationSeverity.INFO,
                timestamp = "Dün",
                isRead = true
            )
        )
    }
}