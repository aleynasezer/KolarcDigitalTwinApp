package com.kolarc.digitaltwin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolarc.digitaltwin.di.KoinProvider
import com.kolarc.digitaltwin.domain.model.NotificationItem
import com.kolarc.digitaltwin.domain.model.NotificationSeverity
import com.kolarc.digitaltwin.presentation.notification.NotificationUiState
import com.kolarc.digitaltwin.presentation.notification.NotificationViewModel

@Composable
fun NotificationScreen() {
    val viewModel = remember {
        KoinProvider.koin.get<NotificationViewModel>()
    }

    val uiState by viewModel.uiState.collectAsState()

    NotificationContent(
        uiState = uiState,
        onSeveritySelected = viewModel::onSeveritySelected,
        onRetry = viewModel::retry
    )
}

@Composable
private fun NotificationContent(
    uiState: NotificationUiState,
    onSeveritySelected: (NotificationSeverity?) -> Unit,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA)),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.errorMessage != null -> {
                NotificationErrorContent(
                    message = uiState.errorMessage,
                    onRetry = onRetry
                )
            }

            else -> {
                NotificationSuccessContent(
                    uiState = uiState,
                    onSeveritySelected = onSeveritySelected
                )
            }
        }
    }
}

@Composable
private fun NotificationSuccessContent(
    uiState: NotificationUiState,
    onSeveritySelected: (NotificationSeverity?) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            NotificationHeader(
                unreadCount = uiState.notifications.count { notification ->
                    !notification.isRead
                }
            )
        }

        item {
            NotificationFilters(
                selectedSeverity = uiState.selectedSeverity,
                onSeveritySelected = onSeveritySelected
            )
        }

        if (uiState.filteredNotifications.isEmpty()) {
            item {
                EmptyNotificationContent()
            }
        } else {
            items(
                items = uiState.filteredNotifications,
                key = { notification ->
                    notification.id
                }
            ) { notification ->
                NotificationCard(
                    notification = notification
                )
            }
        }

        item {
            Spacer(
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun NotificationHeader(
    unreadCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Bildirimler",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E2638)
        )

        Text(
            text = if (unreadCount > 0) {
                "$unreadCount okunmamış bildiriminiz var"
            } else {
                "Tüm bildirimler okundu"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF667085)
        )
    }
}

@Composable
private fun NotificationFilters(
    selectedSeverity: NotificationSeverity?,
    onSeveritySelected: (NotificationSeverity?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selectedSeverity == null,
            onClick = {
                onSeveritySelected(null)
            },
            label = {
                Text("Tümü")
            }
        )

        FilterChip(
            selected = selectedSeverity ==
                    NotificationSeverity.INFO,
            onClick = {
                onSeveritySelected(
                    NotificationSeverity.INFO
                )
            },
            label = {
                Text("Bilgi")
            }
        )

        FilterChip(
            selected = selectedSeverity ==
                    NotificationSeverity.WARNING,
            onClick = {
                onSeveritySelected(
                    NotificationSeverity.WARNING
                )
            },
            label = {
                Text("Uyarı")
            }
        )

        FilterChip(
            selected = selectedSeverity ==
                    NotificationSeverity.CRITICAL,
            onClick = {
                onSeveritySelected(
                    NotificationSeverity.CRITICAL
                )
            },
            label = {
                Text("Kritik")
            }
        )
    }
}

@Composable
private fun NotificationCard(
    notification: NotificationItem
) {
    val severityColor = when (notification.severity) {
        NotificationSeverity.INFO -> Color(0xFF2E90FA)
        NotificationSeverity.WARNING -> Color(0xFFF79009)
        NotificationSeverity.CRITICAL -> Color(0xFFF04438)
    }

    val severityText = when (notification.severity) {
        NotificationSeverity.INFO -> "Bilgi"
        NotificationSeverity.WARNING -> "Uyarı"
        NotificationSeverity.CRITICAL -> "Kritik"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) {
                Color.White
            } else {
                Color(0xFFFFFCF5)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .padding(top = 4.dp)
                    .background(
                        color = severityColor,
                        shape = CircleShape
                    )
            ) {
                Spacer(
                    modifier = Modifier.padding(5.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E2638),
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = notification.timestamp,
                        fontSize = 11.sp,
                        color = Color(0xFF98A2B3)
                    )
                }

                Text(
                    text = notification.message,
                    fontSize = 13.sp,
                    color = Color(0xFF667085)
                )

                Text(
                    text = severityText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = severityColor
                )

                if (!notification.isRead) {
                    Text(
                        text = "Yeni",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF12B76A)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyNotificationContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Bildirim bulunamadı",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E2638)
        )

        Text(
            text = "Seçilen önem seviyesinde bildirim yok.",
            fontSize = 13.sp,
            color = Color(0xFF667085)
        )
    }
}

@Composable
private fun NotificationErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Bildirimler yüklenemedi",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E2638)
        )

        Text(
            text = message,
            color = Color(0xFF667085)
        )

        Button(
            onClick = onRetry
        ) {
            Text("Tekrar dene")
        }
    }
}