package com.kolarc.digitaltwin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolarc.digitaltwin.domain.model.MachineStatus

@Composable
fun LiveViewContent(machines: List<MachineStatus>) {
    val onlineMachines = machines.filter { it.isOnline }
    val offlineMachines = machines.filter { !it.isOnline }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Online ${onlineMachines.size}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                Box(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(8.dp)) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        onlineMachines.chunked(2).forEach { rowItems ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                rowItems.forEach { machine -> Box(modifier = Modifier.weight(1f)) { LiveViewMiniCard(machine = machine) } }
                                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Offline ${offlineMachines.size}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFFC62828))
                Box(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFEBEE), RoundedCornerShape(8.dp)).padding(8.dp)) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        offlineMachines.chunked(2).forEach { rowItems ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                rowItems.forEach { machine -> Box(modifier = Modifier.weight(1f)) { LiveViewMiniCard(machine = machine) } }
                                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LiveViewMiniCard(machine: MachineStatus) {
    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(12.dp).background(color = if (machine.isOnline) Color(0xFF4CAF50) else Color(0xFF78909C), shape = CircleShape))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = machine.serialNumber, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = machine.model, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun DetailedMachineCard(machine: MachineStatus) {
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 3.dp), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = machine.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2C3E50))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(12.dp).background(color = if (machine.isOnline) Color(0xFF4CAF50) else Color(0xFFF44336), shape = CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = if (machine.isOnline) "Çevrimiçi" else "Çevrimdışı", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = if (machine.isOnline) Color(0xFF4CAF50) else Color(0xFFF44336))
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFF0F2F5))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Model: ${machine.model}", fontSize = 13.sp, color = Color.Gray)
                    Text(text = "Seri No: ${machine.serialNumber}", fontSize = 13.sp, color = Color.Gray)
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.End) {
                    Text(text = "Konum: ${machine.location}", fontSize = 13.sp, color = Color(0xFF34495E), fontWeight = FontWeight.Medium)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().background(Color(0xFFF8F9FA), RoundedCornerShape(6.dp)).padding(vertical = 6.dp, horizontal = 10.dp)) {
                Text(text = "Son Bağlantı: ${machine.lastConnected}", fontSize = 11.sp, color = Color.DarkGray)
            }
        }
    }
}