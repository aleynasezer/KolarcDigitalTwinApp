package com.kolarc.digitaltwin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kolarc.digitaltwin.data.repository.MockMachineRepository
import com.kolarc.digitaltwin.domain.model.MachineStatus

@Composable
fun MachineListScreen() {
    val repository = remember { MockMachineRepository() }

    var allMachines by remember {
        mutableStateOf<List<MachineStatus>>(emptyList())
    }

    var selectedTab by remember {
        mutableStateOf(0)
    }

    var searchQuery by remember {
        mutableStateOf("")
    }

    LaunchedEffect(repository) {
        allMachines = repository.getAllMachines()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Overview") }
            )

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Live view") }
            )
        }

        if (selectedTab == 0) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Ara...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (selectedTab) {
                0 -> {
                    val filteredMachines = if (searchQuery.isBlank()) {
                        allMachines
                    } else {
                        allMachines.filter { machine ->
                            machine.name.contains(
                                other = searchQuery,
                                ignoreCase = true
                            )
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(filteredMachines.size) { index ->
                            DetailedMachineCard(
                                machine = filteredMachines[index]
                            )
                        }
                    }
                }

                1 -> {
                    LiveViewContent(
                        machines = allMachines
                    )
                }
            }
        }
    }
}