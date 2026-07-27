package com.kolarc.digitaltwin.presentation.notification

import com.kolarc.digitaltwin.domain.model.NotificationSeverity
import com.kolarc.digitaltwin.domain.usecase.FilterNotificationsUseCase
import com.kolarc.digitaltwin.domain.usecase.GetNotificationsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val filterNotificationsUseCase: FilterNotificationsUseCase
) {

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    private val _uiState = MutableStateFlow(
        NotificationUiState()
    )

    val uiState: StateFlow<NotificationUiState> =
        _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    fun onSeveritySelected(
        severity: NotificationSeverity?
    ) {
        val notifications = _uiState.value.notifications

        _uiState.value = _uiState.value.copy(
            selectedSeverity = severity,
            filteredNotifications = filterNotificationsUseCase(
                notifications = notifications,
                selectedSeverity = severity
            )
        )
    }

    fun retry() {
        loadNotifications()
    }

    private fun loadNotifications() {
        _uiState.value = NotificationUiState(
            isLoading = true
        )

        scope.launch {
            try {
                val notifications = getNotificationsUseCase()

                _uiState.value = NotificationUiState(
                    isLoading = false,
                    notifications = notifications,
                    filteredNotifications = notifications,
                    selectedSeverity = null,
                    errorMessage = null
                )
            } catch (exception: Exception) {
                _uiState.value = NotificationUiState(
                    isLoading = false,
                    errorMessage = exception.message
                        ?: "Bildirimler yüklenemedi"
                )
            }
        }
    }
}