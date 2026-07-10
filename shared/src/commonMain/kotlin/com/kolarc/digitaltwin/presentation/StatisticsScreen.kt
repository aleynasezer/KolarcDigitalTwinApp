package com.kolarc.digitaltwin.presentation

import androidx.compose.foundation.BorderStroke
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
import kotlin.random.Random

data class ChartBarData(
    val dateText: String,
    val value: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen() {
    var isValueMenuOpen by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf("Duration (h)") }

    var isDateMenuOpen by remember { mutableStateOf(false) }
    var selectedDateRange by remember { mutableStateOf("Last 7 days") }

    var isGroupMenuOpen by remember { mutableStateOf(false) }
    var selectedGroupBy by remember { mutableStateOf("Day") }

    var isSecondGroupingActive by remember { mutableStateOf(false) }
    var isSecondGroupMenuOpen by remember { mutableStateOf(false) }
    var selectedSecondGroupBy by remember { mutableStateOf("Select...") }

    // Web görüntüsündeki gibi seçili olan grafik türünü takip etmek için state
    var selectedChartType by remember { mutableStateOf("Bar chart (grouped)") }

    // Web panelindeki orijinal tarihler ve barlar (Üstte sayısal etiketler kaldırıldı)
    var barDataList by remember {
        mutableStateOf(
            listOf(
                ChartBarData("03/07/2026", 0f),
                ChartBarData("04/07/2026", 0f),
                ChartBarData("05/07/2026", 0f),
                ChartBarData("06/07/2026", 3.20f), // Fotoğraftaki en yüksek bar
                ChartBarData("07/07/2026", 2.30f),
                ChartBarData("08/07/2026", 1.20f),
                ChartBarData("09/07/2026", 0.90f),
                ChartBarData("10/07/2026", 0f)
            )
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- FİLTRELEME ALANI ---
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    Column {
                        Text("Value to be displayed", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        ExposedDropdownMenuBox(expanded = isValueMenuOpen, onExpandedChange = { isValueMenuOpen = it }) {
                            OutlinedTextField(
                                value = selectedValue,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isValueMenuOpen) },
                                modifier = Modifier.fillMaxWidth().menuAnchor(),
                                shape = RoundedCornerShape(6.dp),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFD1D5DB), unfocusedBorderColor = Color(0xFFE5E7EB))
                            )
                            ExposedDropdownMenu(expanded = isValueMenuOpen, onDismissRequest = { isValueMenuOpen = false }) {
                                listOf("Duration (h)", "Total number of welds", "Energy consumption (kWh)", "Wire consumption (kg)", "Gas consumption (l)").forEach { item ->
                                    DropdownMenuItem(text = { Text(item, fontSize = 14.sp) }, onClick = { selectedValue = item; isValueMenuOpen = false })
                                }
                            }
                        }
                    }

                    Column {
                        Text("Date range", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        ExposedDropdownMenuBox(expanded = isDateMenuOpen, onExpandedChange = { isDateMenuOpen = it }) {
                            OutlinedTextField(
                                value = selectedDateRange,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDateMenuOpen) },
                                modifier = Modifier.fillMaxWidth().menuAnchor(),
                                shape = RoundedCornerShape(6.dp),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFD1D5DB), unfocusedBorderColor = Color(0xFFE5E7EB))
                            )
                            ExposedDropdownMenu(expanded = isDateMenuOpen, onDismissRequest = { isDateMenuOpen = false }) {
                                listOf("Last 7 days", "Last week", "Last 30 days", "Last month", "Today").forEach { item ->
                                    DropdownMenuItem(text = { Text(item, fontSize = 14.sp) }, onClick = { selectedDateRange = item; isDateMenuOpen = false })
                                }
                            }
                        }
                    }

                    Column {
                        Text("Grouped by", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        ExposedDropdownMenuBox(expanded = isGroupMenuOpen, onExpandedChange = { isGroupMenuOpen = it }) {
                            OutlinedTextField(
                                value = selectedGroupBy,
                                onValueChange = {},
                                readOnly = true,
                                leadingIcon = { Text("✕", modifier = Modifier.padding(start = 8.dp), color = Color.Gray, fontSize = 12.sp) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGroupMenuOpen) },
                                modifier = Modifier.fillMaxWidth().menuAnchor(),
                                shape = RoundedCornerShape(6.dp),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFD1D5DB), unfocusedBorderColor = Color(0xFFE5E7EB))
                            )
                            ExposedDropdownMenu(expanded = isGroupMenuOpen, onDismissRequest = { isGroupMenuOpen = false }) {
                                listOf("Hour", "Day", "Week", "Month", "Year", "Machine").forEach { item ->
                                    DropdownMenuItem(text = { Text(item, fontSize = 14.sp) }, onClick = { selectedGroupBy = item; isGroupMenuOpen = false })
                                }
                            }
                        }
                    }

                    if (isSecondGroupingActive) {
                        Column {
                            Text("and then grouped by", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            Spacer(modifier = Modifier.height(4.dp))
                            ExposedDropdownMenuBox(expanded = isSecondGroupMenuOpen, onExpandedChange = { isSecondGroupMenuOpen = it }) {
                                OutlinedTextField(
                                    value = selectedSecondGroupBy,
                                    onValueChange = {},
                                    readOnly = true,
                                    leadingIcon = {
                                        IconButton(onClick = { isSecondGroupingActive = false; selectedSecondGroupBy = "Select..." }) {
                                            Text("✕", color = Color.Gray, fontSize = 12.sp)
                                        }
                                    },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSecondGroupMenuOpen) },
                                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                                    shape = RoundedCornerShape(6.dp),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFD1D5DB), unfocusedBorderColor = Color(0xFFE5E7EB))
                                )
                                ExposedDropdownMenu(expanded = isSecondGroupMenuOpen, onDismissRequest = { isSecondGroupMenuOpen = false }) {
                                    listOf("Hour", "Day", "Week", "Month", "Machine").forEach { item ->
                                        DropdownMenuItem(text = { Text(item, fontSize = 14.sp) }, onClick = { selectedSecondGroupBy = item; isSecondGroupMenuOpen = false })
                                    }
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (!isSecondGroupingActive) {
                            OutlinedButton(
                                onClick = { isSecondGroupingActive = true },
                                shape = RoundedCornerShape(4.dp),
                                border = BorderStroke(1.dp, Color(0xFFD1D5DB)),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                                modifier = Modifier.weight(1.3f),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text("+ Add another grouping", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                        }

                        Button(
                            onClick = {
                                val prefix = when (selectedGroupBy) {
                                    "Week" -> "W"
                                    "Year" -> "202"
                                    "Month" -> "M"
                                    else -> ""
                                }

                                barDataList = List(8) { index ->
                                    val hasValue = Random.nextBoolean()
                                    val randomVal = if (hasValue) (Random.nextInt(5, 38).toFloat() / 10f) else 0f

                                    val dateText = when (selectedGroupBy) {
                                        "Week" -> "${prefix}${22 + index}/2026"
                                        "Hour" -> {
                                            val hour = (8 + index * 2) % 24
                                            val hourStr = if (hour < 10) "0$hour" else "$hour"
                                            "$hourStr:00"
                                        }
                                        "Year" -> "${prefix}${index}"
                                        "Month" -> "${prefix}${index + 1}/2026"
                                        else -> {
                                            val day = index + 3
                                            val dayStr = if (day < 10) "0$day" else "$day"
                                            "$dayStr/07/2026"
                                        }
                                    }
                                    ChartBarData(dateText, randomVal)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5E7EB)),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.weight(0.7f)
                        ) {
                            Text("Apply", color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // --- CHART TYPE ALANI (WEB'E UYARLANDI) ---
        item {
            Column {
                Text("Chart type", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E2638))
                Spacer(modifier = Modifier.height(8.dp))

                val scrollState = rememberScrollState()
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(scrollState),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val types = listOf("Bar chart (stacked)", "Bar chart (grouped)", "Line chart", "Matrix chart(Pie chart)")
                    types.forEach { type ->
                        val isSelected = selectedChartType == type
                        Button(
                            onClick = { selectedChartType = type },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) Color(0xFF3B82F6) else Color.White
                            ),
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(1.dp, Color(0xFF3B82F6)),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = type,
                                color = if (isSelected) Color.White else Color(0xFF3B82F6),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // --- SEÇKİN ANALYTICS GRAPH VIEW ---
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Analytics View", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                        Text("➕ ➖ 🔍 ✋ 🏠 ☰", fontSize = 12.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Yatay kaydırılabilir Grafik Gövdesi (Tarih formatları uzun olduğu için taşmasın)
                    val graphScrollState = rememberScrollState()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .background(Color(0xFFFAFAFA))
                            .padding(top = 16.dp, bottom = 8.dp, start = 4.dp, end = 4.dp)
                    ) {
                        // Sol Y Ekseni Cetveli (Sabit kalıyor)
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(52.dp)
                                .padding(bottom = 26.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("04:00:00", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Text("03:00:00", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Text("02:00:00", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Text("01:00:00", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Text("00:00:00", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                        }

                        // Kaydırılabilir Barlar ve X Ekseni
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .horizontalScroll(graphScrollState),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            barDataList.forEach { barData ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.width(68.dp).fillMaxHeight(),
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    // Sütun Bar Alanı (Web'deki gibi barların üzerinde sayılar yazmıyor)
                                    Box(
                                        modifier = Modifier.weight(1f).fillMaxWidth(),
                                        contentAlignment = Alignment.BottomCenter
                                    ) {
                                        if (barData.value > 0f) {
                                            Box(
                                                modifier = Modifier
                                                    .width(36.dp)
                                                    .height((barData.value * 48).dp)
                                                    .background(Color(0xFF3B82F6), RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp))
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    HorizontalDivider(
                                        color = Color.LightGray.copy(alpha = 0.5f),
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    // X Ekseni Uzun Tarih Yazısı (Web'deki gibi: 06/07/2026)
                                    Text(
                                        text = barData.dateText,
                                        fontSize = 9.sp,
                                        color = Color.DarkGray,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}