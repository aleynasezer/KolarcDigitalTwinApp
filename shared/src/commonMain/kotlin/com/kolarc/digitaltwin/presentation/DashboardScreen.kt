package com.kolarc.digitaltwin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import com.kolarc.digitaltwin.domain.model.DashboardSummary
import com.kolarc.digitaltwin.domain.model.SensorReading
import com.kolarc.digitaltwin.presentation.dashboard.DashboardUiState
import com.kolarc.digitaltwin.presentation.dashboard.DashboardViewModel
import kotlin.math.roundToInt

@Composable
fun DashboardScreen() {
    val viewModel = remember {
        KoinProvider.koin.get<DashboardViewModel>()
    }

    val uiState by viewModel.uiState.collectAsState()

    DashboardContent(
        uiState = uiState,
        onRetry = viewModel::retry
    )
}

@Composable
private fun DashboardContent(
    uiState: DashboardUiState,
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
                DashboardErrorContent(
                    message = uiState.errorMessage,
                    onRetry = onRetry
                )
            }

            uiState.summary != null -> {
                DashboardSuccessContent(
                    summary = uiState.summary
                )
            }
        }
    }
}

@Composable
private fun DashboardSuccessContent(
    summary: DashboardSummary
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DashboardHeader()
        }

        item {
            KpiSection(
                summary = summary
            )
        }

        item {
            SensorSummarySection(
                sensorReadings = summary.sensorReadings
            )
        }

        item {
            SensorChartCard(
                title = "Sıcaklık İzleme",
                unit = "°C",
                values = summary.sensorReadings.map { reading ->
                    reading.temperature
                },
                labels = summary.sensorReadings.map { reading ->
                    reading.label
                },
                barColor = Color(0xFFF79009)
            )
        }

        item {
            SensorChartCard(
                title = "Titreşim İzleme",
                unit = "mm/s",
                values = summary.sensorReadings.map { reading ->
                    reading.vibration
                },
                labels = summary.sensorReadings.map { reading ->
                    reading.label
                },
                barColor = Color(0xFF7F56D9)
            )
        }

        item {
            SensorChartCard(
                title = "Enerji Tüketimi",
                unit = "kWh",
                values = summary.sensorReadings.map { reading ->
                    reading.energyConsumption
                },
                labels = summary.sensorReadings.map { reading ->
                    reading.label
                },
                barColor = Color(0xFF2E90FA)
            )
        }

        item {
            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
    }
}

@Composable
private fun DashboardHeader() {
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
            text = "Genel Bakış",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E2638)
        )

        Text(
            text = "Makine ve sensör verilerinin güncel özeti",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF667085)
        )
    }
}

@Composable
private fun KpiSection(
    summary: DashboardSummary
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardKpiCard(
                title = "Çevrimiçi",
                value = summary.onlineMachineCount.toString(),
                description = "Aktif makineler",
                accentColor = Color(0xFF12B76A),
                modifier = Modifier.weight(1f)
            )

            DashboardKpiCard(
                title = "Çevrimdışı",
                value = summary.offlineMachineCount.toString(),
                description = "Bağlantısı olmayan",
                accentColor = Color(0xFFF04438),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardKpiCard(
                title = "Ortalama OEE",
                value = "${summary.averageOee}%",
                description = "Genel verimlilik",
                accentColor = Color(0xFF2E90FA),
                modifier = Modifier.weight(1f)
            )

            DashboardKpiCard(
                title = "Aktif Alarm",
                value = summary.activeAlarmCount.toString(),
                description = "Müdahale bekleyen",
                accentColor = Color(0xFFF79009),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DashboardKpiCard(
    title: String,
    value: String,
    description: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.heightIn(
            min = 130.dp
        ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(5.dp)
                    .background(
                        color = accentColor,
                        shape = RoundedCornerShape(50)
                    )
            )

            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF667085)
            )

            Text(
                text = value,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E2638)
            )

            Text(
                text = description,
                fontSize = 11.sp,
                color = Color(0xFF98A2B3)
            )
        }
    }
}

@Composable
private fun SensorSummarySection(
    sensorReadings: List<SensorReading>
) {
    val latestReading = sensorReadings.lastOrNull()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Anlık Sensör Değerleri",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E2638)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SensorValueCard(
                title = "Sıcaklık",
                value = latestReading?.temperature?.let { value ->
                    "$value °C"
                } ?: "-",
                modifier = Modifier.weight(1f)
            )

            SensorValueCard(
                title = "Titreşim",
                value = latestReading?.vibration?.let { value ->
                    "$value mm/s"
                } ?: "-",
                modifier = Modifier.weight(1f)
            )

            SensorValueCard(
                title = "Enerji",
                value = latestReading?.energyConsumption?.let { value ->
                    "$value kWh"
                } ?: "-",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SensorValueCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 14.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                fontSize = 11.sp,
                color = Color(0xFF667085)
            )

            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E2638)
            )
        }
    }
}

@Composable
private fun SensorChartCard(
    title: String,
    unit: String,
    values: List<Double>,
    labels: List<String>,
    barColor: Color
) {
    val maxValue = values.maxOrNull() ?: 1.0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E2638)
                )

                val latestValue = values.lastOrNull()

                Text(
                    text = latestValue?.let { value ->
                        "$value $unit"
                    } ?: "-",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = barColor
                )
            }

            HorizontalDivider(
                color = Color(0xFFEAECF0)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                values.forEachIndexed { index, value ->
                    val ratio = if (maxValue > 0.0) {
                        value / maxValue
                    } else {
                        0.0
                    }

                    val barHeight = (
                            30 + ratio * 80
                            ).roundToInt().dp

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = value.toString(),
                            fontSize = 9.sp,
                            color = Color(0xFF667085)
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(barHeight)
                                .background(
                                    color = barColor.copy(
                                        alpha = 0.85f
                                    ),
                                    shape = RoundedCornerShape(
                                        topStart = 5.dp,
                                        topEnd = 5.dp
                                    )
                                )
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = labels.getOrElse(index) {
                                ""
                            },
                            fontSize = 9.sp,
                            color = Color(0xFF98A2B3)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Dashboard yüklenemedi",
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