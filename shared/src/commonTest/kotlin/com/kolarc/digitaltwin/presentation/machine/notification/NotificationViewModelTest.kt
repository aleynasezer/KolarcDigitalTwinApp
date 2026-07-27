package com.kolarc.digitaltwin.presentation.notification

import com.kolarc.digitaltwin.domain.model.NotificationItem
import com.kolarc.digitaltwin.domain.model.NotificationSeverity
import com.kolarc.digitaltwin.domain.repository.NotificationRepository
import com.kolarc.digitaltwin.domain.usecase.FilterNotificationsUseCase
import com.kolarc.digitaltwin.domain.usecase.GetNotificationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class NotificationViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun notificationsLoadSuccessfully() = runTest(testDispatcher) {
        val viewModel = createViewModel()

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
        assertEquals(
            expected = 3,
            actual = state.notifications.size
        )
        assertEquals(
            expected = 3,
            actual = state.filteredNotifications.size
        )
    }

    @Test
    fun selectingCriticalSeverityFiltersNotifications() =
        runTest(testDispatcher) {
            val viewModel = createViewModel()

            advanceUntilIdle()

            viewModel.onSeveritySelected(
                NotificationSeverity.CRITICAL
            )

            val state = viewModel.uiState.value

            assertEquals(
                expected = NotificationSeverity.CRITICAL,
                actual = state.selectedSeverity
            )
            assertEquals(
                expected = 1,
                actual = state.filteredNotifications.size
            )
            assertEquals(
                expected = NotificationSeverity.CRITICAL,
                actual = state.filteredNotifications.first().severity
            )
        }

    @Test
    fun selectingNullSeverityShowsAllNotifications() =
        runTest(testDispatcher) {
            val viewModel = createViewModel()

            advanceUntilIdle()

            viewModel.onSeveritySelected(
                NotificationSeverity.CRITICAL
            )

            viewModel.onSeveritySelected(null)

            val state = viewModel.uiState.value

            assertNull(state.selectedSeverity)
            assertEquals(
                expected = 3,
                actual = state.filteredNotifications.size
            )
        }

    private fun createViewModel(): NotificationViewModel {
        val repository = FakeNotificationRepository()

        return NotificationViewModel(
            getNotificationsUseCase = GetNotificationsUseCase(
                repository = repository
            ),
            filterNotificationsUseCase =
                FilterNotificationsUseCase()
        )
    }

    private class FakeNotificationRepository :
        NotificationRepository {

        override suspend fun getNotifications():
                List<NotificationItem> {
            return listOf(
                NotificationItem(
                    id = "1",
                    title = "Critical notification",
                    message = "Critical message",
                    severity = NotificationSeverity.CRITICAL,
                    timestamp = "09:00",
                    isRead = false
                ),
                NotificationItem(
                    id = "2",
                    title = "Warning notification",
                    message = "Warning message",
                    severity = NotificationSeverity.WARNING,
                    timestamp = "08:30",
                    isRead = false
                ),
                NotificationItem(
                    id = "3",
                    title = "Info notification",
                    message = "Info message",
                    severity = NotificationSeverity.INFO,
                    timestamp = "08:00",
                    isRead = true
                )
            )
        }
    }
}