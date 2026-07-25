package com.kolarc.digitaltwin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolarc.digitaltwin.di.KoinProvider
import com.kolarc.digitaltwin.domain.model.WeldRecord
import com.kolarc.digitaltwin.domain.repository.WeldRecordRepository

@Composable
fun KaynaklarScreen() {
    val repository = remember {
        KoinProvider.koin.get<WeldRecordRepository>()
    }

    var selectedWeldTypeTab by remember {
        mutableIntStateOf(0)
    }

    val weldTypes = remember {
        listOf("MIG", "MMA", "TIG", "SAW")
    }

    val currentWeldList = remember(selectedWeldTypeTab) {
        mutableStateListOf<WeldRecord>().apply {
            addAll(
                repository.getWeldRecords(
                    type = weldTypes[selectedWeldTypeTab]
                )
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
    ) {
        item {
            TabRow(
                selectedTabIndex = selectedWeldTypeTab,
                containerColor = Color.White,
                contentColor = Color(0xFF1E2638)
            ) {
                weldTypes.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedWeldTypeTab == index,
                        onClick = {
                            selectedWeldTypeTab = index
                        },
                        text = {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                }
            }
        }

        item {
            val horizontalScrollState = rememberScrollState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(horizontalScrollState)
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.width(1150.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFEFEFEF))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(
                            modifier = Modifier.width(40.dp)
                        )

                        val headers = listOf(
                            "Time stamp" to 180,
                            "Duration [s]" to 100,
                            "Location" to 100,
                            "Serial number" to 120,
                            "Description" to 140,
                            "Organization" to 120,
                            "Limit violations" to 130,
                            "Error" to 100
                        )

                        headers.forEach { (text, width) ->
                            Text(
                                text = text,
                                modifier = Modifier.width(width.dp),
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray,
                                fontSize = 14.sp
                            )
                        }
                    }

                    if (currentWeldList.isEmpty()) {
                        Text(
                            text = "No results to display.",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    } else {
                        currentWeldList.forEach { record ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "📄",
                                    fontSize = 18.sp,
                                    modifier = Modifier.width(40.dp)
                                )

                                Text(
                                    text = record.timestamp,
                                    modifier = Modifier.width(180.dp),
                                    fontSize = 13.sp
                                )

                                Text(
                                    text = record.duration,
                                    modifier = Modifier.width(100.dp),
                                    fontSize = 13.sp
                                )

                                Text(
                                    text = record.location,
                                    modifier = Modifier.width(100.dp),
                                    fontSize = 13.sp
                                )

                                Text(
                                    text = record.serialNumber,
                                    modifier = Modifier.width(120.dp),
                                    fontSize = 13.sp
                                )

                                Text(
                                    text = record.description,
                                    modifier = Modifier.width(140.dp),
                                    fontSize = 13.sp
                                )

                                Text(
                                    text = record.organization,
                                    modifier = Modifier.width(120.dp),
                                    fontSize = 13.sp
                                )

                                Text(
                                    text = record.limitViolation,
                                    modifier = Modifier.width(130.dp),
                                    fontSize = 13.sp
                                )

                                Text(
                                    text = record.error,
                                    modifier = Modifier.width(100.dp),
                                    fontSize = 13.sp
                                )
                            }

                            HorizontalDivider(
                                color = Color.LightGray.copy(
                                    alpha = 0.5f
                                )
                            )
                        }
                    }

                    Button(
                        onClick = {
                            currentWeldList.addAll(
                                repository.getMoreWeldRecords()
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE0E0E0)
                        ),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Show more results",
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}