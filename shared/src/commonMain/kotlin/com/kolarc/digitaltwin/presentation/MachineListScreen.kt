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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kolarc.digitaltwin.di.KoinProvider
import com.kolarc.digitaltwin.presentation.machine.MachineListUiState
import com.kolarc.digitaltwin.presentation.machine.MachineListViewModel

@Composable
fun MachineListScreen(
    onMachineClick: (String) -> Unit
) {
    val viewModel = remember {
        KoinProvider.koin.get<MachineListViewModel>()
    }

    val uiState by viewModel.uiState.collectAsState()

    MachineListContent(
        uiState = uiState,
        onTabSelected = viewModel::onTabSelected,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onRetry = viewModel::retry,
        onMachineClick = onMachineClick
    )
}

@Composable
private fun MachineListContent(
    uiState: MachineListUiState,
    onTabSelected: (Int) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onRetry: () -> Unit,
    onMachineClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
    ) {
        TabRow(
            selectedTabIndex = uiState.selectedTab,
            containerColor = Color.White
        ) {
            Tab(
                selected = uiState.selectedTab ==
                        MachineListUiState.OVERVIEW_TAB_INDEX,
                onClick = {
                    onTabSelected(
                        MachineListUiState.OVERVIEW_TAB_INDEX
                    )
                },
                text = {
                    Text("Overview")
                }
            )

            Tab(
                selected = uiState.selectedTab ==
                        MachineListUiState.LIVE_VIEW_TAB_INDEX,
                onClick = {
                    onTabSelected(
                        MachineListUiState.LIVE_VIEW_TAB_INDEX
                    )
                },
                text = {
                    Text("Live view")
                }
            )
        }

        if (
            uiState.selectedTab ==
            MachineListUiState.OVERVIEW_TAB_INDEX
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChanged,
                placeholder = {
                    Text("Ara...")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.errorMessage != null -> {
                    MachineListErrorContent(
                        message = uiState.errorMessage,
                        onRetry = onRetry
                    )
                }

                uiState.selectedTab ==
                        MachineListUiState.OVERVIEW_TAB_INDEX -> {
                    MachineOverviewContent(
                        uiState = uiState,
                        onMachineClick = onMachineClick
                    )
                }

                uiState.selectedTab ==
                        MachineListUiState.LIVE_VIEW_TAB_INDEX -> {
                    LiveViewContent(
                        machines = uiState.machines
                    )
                }
            }
        }
    }
}

@Composable
private fun MachineOverviewContent(
    uiState: MachineListUiState,
    onMachineClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            bottom = 16.dp
        )
    ) {
        items(
            count = uiState.filteredMachines.size,
            key = { index ->
                uiState.filteredMachines[index].id
            }
        ) { index ->
            val machine = uiState.filteredMachines[index]

            DetailedMachineCard(
                machine = machine,
                onClick = {
                    onMachineClick(machine.id)
                }
            )
        }
    }
}

@Composable
private fun MachineListErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(24.dp)
    ) {
        Text(text = message)

        Button(
            onClick = onRetry
        ) {
            Text("Tekrar dene")
        }
    }
}