package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.data.repository.FakeNotificationRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetNotificationsUseCaseTest {

    @Test
    fun notificationsRepositorydenGetirilir() = runTest {

        val repository = FakeNotificationRepository()

        val useCase = GetNotificationsUseCase(
            repository = repository
        )

        val result = useCase()

        assertEquals(
            expected = 4,
            actual = result.size
        )

        assertEquals(
            expected = "Makine Çevrimdışı",
            actual = result.first().title
        )
    }
}