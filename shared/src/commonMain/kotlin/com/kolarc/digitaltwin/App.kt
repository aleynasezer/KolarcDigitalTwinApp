package com.kolarc.digitaltwin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolarc.digitaltwin.presentation.*
import kotlinx.coroutines.launch

// Menü öğeleri için basit bir veri sınıfı
data class DrawerMenuItem(val id: String, val title: String, val iconText: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf("makineler") }

    val menuItems = listOf(
        DrawerMenuItem("dashboard", "Dashboard", "D"),
        DrawerMenuItem("makineler", "Makineler", "M"),
        DrawerMenuItem("kaynaklar", "Kaynaklar", "K"),
        DrawerMenuItem("istatistikler", "İstatistikler", "İ"),
        DrawerMenuItem("raporlar", "Raporlar", "R"),
        DrawerMenuItem("bakim", "Bakım", "B")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color(0xFF1E2638), modifier = Modifier.width(280.dp)) {
                Spacer(modifier = Modifier.height(24.dp))
                Text("KOLARC CLOUD", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(24.dp))

                menuItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.title, color = Color.White) },
                        selected = currentScreen == item.id,
                        onClick = { currentScreen = item.id; scope.launch { drawerState.close() } },
                        colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = Color(0xFF2C3E50))
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                Surface(color = Color(0xFF1E2638), modifier = Modifier.fillMaxWidth().height(60.dp), shadowElevation = 4.dp) {
                    Row(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Text("☰", color = Color.White, fontSize = 24.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = menuItems.find { it.id == currentScreen }?.title ?: "Kolarc",
                            color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (currentScreen) {
                    "makineler" -> MachineListScreen()
                    "kaynaklar" -> KaynaklarScreen()
                    "raporlar" -> ReportsScreen(onNavigateToCreate = { currentScreen = "create_report" })
                    "create_report" -> CreateReportScreen(onBack = { currentScreen = "raporlar" })

                    // Eksik olan sayfalar için "Yapım Aşamasında" uyarısı
                    else -> {
                        val screenTitle = menuItems.find { it.id == currentScreen }?.title ?: ""
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("⚠️", fontSize = 40.sp)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("$screenTitle Ekranı Yakında", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}