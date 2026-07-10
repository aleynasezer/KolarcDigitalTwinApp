package com.kolarc.digitaltwin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kolarc.digitaltwin.data.repository.MockMachineRepository

@Composable
fun MachineListScreen() {
    val repository = remember { MockMachineRepository() }
    val allMachines = remember { repository.getAllMachines() }

    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F7FA))) {
        // Tablar
        TabRow(selectedTabIndex = selectedTab, containerColor = Color.White) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Overview") })
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Live view") })
        }

        // Arama Çubuğu (Sadece Overview'da görünür)
        if (selectedTab == 0) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Ara...") },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(8.dp)
            )
        }

        // İÇERİK GEÇİŞİ
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> {
                    // Overview İçeriği
                    val filtered = if (searchQuery.isEmpty()) allMachines
                    else allMachines.filter { it.name.contains(searchQuery, true) }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(filtered.size) { index ->
                            DetailedMachineCard(machine = filtered[index])
                        }
                    }
                }
                1 -> {
                    // Live View İçeriği ( senin kendi yazdığın fonksiyon )
                    LiveViewContent(machines = allMachines)
                }
            }
        }
    }
}