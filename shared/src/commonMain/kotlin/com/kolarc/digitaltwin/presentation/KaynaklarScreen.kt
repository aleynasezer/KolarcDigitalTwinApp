package com.kolarc.digitaltwin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolarc.digitaltwin.data.repository.MockMachineRepository

@Composable
fun KaynaklarScreen() {
    val repository = remember { MockMachineRepository() }
    var selectedWeldTypeTab by remember { mutableStateOf(0) }
    val weldTypes = listOf("MIG", "MMA", "TIG", "SAW")

    // Seçilen tipe göre veriyi al
    val currentWeldList = remember(selectedWeldTypeTab) {
        mutableStateListOf<com.kolarc.digitaltwin.domain.model.WeldRecord>().apply {
            addAll(repository.getWeldRecords(weldTypes[selectedWeldTypeTab]))
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F7FA))) {
        item {
            TabRow(selectedTabIndex = selectedWeldTypeTab, containerColor = Color.White, contentColor = Color(0xFF1E2638)) {
                weldTypes.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedWeldTypeTab == index,
                        onClick = { selectedWeldTypeTab = index },
                        text = { Text(title, fontWeight = FontWeight.Bold) }
                    )
                }
            }
        }

        item {
            val horizontalScrollState = rememberScrollState()
            Box(modifier = Modifier.fillMaxWidth().horizontalScroll(horizontalScrollState).background(Color.White)) {
                Column(modifier = Modifier.width(1150.dp)) {
                    // Tablo Başlıkları
                    Row(
                        modifier = Modifier.fillMaxWidth().background(Color(0xFFEFEFEF)).padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(40.dp))
                        val headers = listOf("Time stamp" to 180, "Duration [s]" to 100, "Location" to 100, "Serial number" to 120, "Description" to 140, "Organization" to 120, "Limit violations" to 130, "Error" to 100)
                        headers.forEach { (text, width) ->
                            Text(text, modifier = Modifier.width(width.dp), fontWeight = FontWeight.Bold, color = Color.DarkGray, fontSize = 14.sp)
                        }
                    }

                    // Veri Satırları
                    if (currentWeldList.isEmpty()) {
                        Text("No results to display.", modifier = Modifier.padding(16.dp), color = Color.Gray)
                    } else {
                        currentWeldList.forEach { record ->
                            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text("📄", fontSize = 18.sp, modifier = Modifier.width(40.dp))
                                Text(record.timestamp, modifier = Modifier.width(180.dp), fontSize = 13.sp)
                                Text(record.duration, modifier = Modifier.width(100.dp), fontSize = 13.sp)
                                Text(record.location, modifier = Modifier.width(100.dp), fontSize = 13.sp)
                                Text(record.serialNumber, modifier = Modifier.width(120.dp), fontSize = 13.sp)
                                Text(record.description, modifier = Modifier.width(140.dp), fontSize = 13.sp)
                                Text(record.organization, modifier = Modifier.width(120.dp), fontSize = 13.sp)
                                Text(record.limitViolation, modifier = Modifier.width(130.dp), fontSize = 13.sp)
                                Text(record.error, modifier = Modifier.width(100.dp), fontSize = 13.sp)
                            }
                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                        }
                    }

                    // Show More Butonu
                    Button(
                        onClick = { currentWeldList.addAll(repository.getMoreWeldRecords()) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Show more results", color = Color.Black)
                    }
                }
            }
        }
    }
}