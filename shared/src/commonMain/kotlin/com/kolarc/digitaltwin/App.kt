package com.kolarc.digitaltwin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolarc.digitaltwin.presentation.CreateReportScreen
import com.kolarc.digitaltwin.presentation.DashboardScreen
import com.kolarc.digitaltwin.presentation.KaynaklarScreen
import com.kolarc.digitaltwin.presentation.ReportsScreen
import com.kolarc.digitaltwin.presentation.StatisticsScreen
import com.kolarc.digitaltwin.presentation.navigation.AppNavigation
import kotlinx.coroutines.launch

data class DrawerMenuItem(
    val id: String,
    val title: String,
    val iconText: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()

    var currentScreen by remember {
        mutableStateOf("dashboard")
    }

    val menuItems = remember {
        listOf(
            DrawerMenuItem(
                id = "dashboard",
                title = "Dashboard",
                iconText = "D"
            ),
            DrawerMenuItem(
                id = "makineler",
                title = "Makineler",
                iconText = "M"
            ),
            DrawerMenuItem(
                id = "kaynaklar",
                title = "Kaynaklar",
                iconText = "K"
            ),
            DrawerMenuItem(
                id = "istatistikler",
                title = "İstatistikler",
                iconText = "İ"
            ),
            DrawerMenuItem(
                id = "raporlar",
                title = "Raporlar",
                iconText = "R"
            ),
            DrawerMenuItem(
                id = "bakim",
                title = "Bakım",
                iconText = "B"
            )
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFF1E2638),
                modifier = Modifier.width(280.dp)
            ) {
                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Text(
                    text = "KOLARC CLOUD",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(24.dp)
                )

                menuItems.forEach { item ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.title,
                                color = Color.White
                            )
                        },
                        selected = currentScreen == item.id,
                        onClick = {
                            currentScreen = item.id

                            scope.launch {
                                drawerState.close()
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFF2C3E50)
                        )
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                Surface(
                    color = Color(0xFF1E2638),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Text(
                                text = "☰",
                                color = Color.White,
                                fontSize = 24.sp
                            )
                        }

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text(
                            text = menuItems
                                .find { item ->
                                    item.id == currentScreen
                                }
                                ?.title
                                ?: "Kolarc",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (currentScreen) {
                    "dashboard" -> {
                        DashboardScreen()
                    }

                    "makineler" -> {
                        AppNavigation()
                    }

                    "kaynaklar" -> {
                        KaynaklarScreen()
                    }

                    "istatistikler" -> {
                        StatisticsScreen()
                    }

                    "raporlar" -> {
                        ReportsScreen(
                            onNavigateToCreate = {
                                currentScreen = "create_report"
                            }
                        )
                    }

                    "create_report" -> {
                        CreateReportScreen(
                            onBack = {
                                currentScreen = "raporlar"
                            }
                        )
                    }

                    else -> {
                        val screenTitle = menuItems
                            .find { item ->
                                item.id == currentScreen
                            }
                            ?.title
                            .orEmpty()

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "⚠️",
                                    fontSize = 40.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(16.dp)
                                )

                                Text(
                                    text = "$screenTitle Ekranı Yakında",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}