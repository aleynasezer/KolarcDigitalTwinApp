package com.kolarc.digitaltwin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReportsScreen(onNavigateToCreate: () -> Unit) { // Parametre eklendi
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // 1. ÜST KISIM
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Raporlar", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E2638))

            // Butona tıklandığında onNavigateToCreate fonksiyonu çalışır
            Button(
                onClick = { onNavigateToCreate() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                shape = RoundedCornerShape(4.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Rapor Oluştur", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. TABLO KARTI
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Ara...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    shape = RoundedCornerShape(4.dp)
                )

                Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFF9FAFB)).padding(16.dp)) {
                    Text("Ad", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Gösterilecek Değer", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Tarih Aralığı", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                }

                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    Text("Henüz bir rapor oluşturulmamış.", color = Color.Gray)
                }
            }
        }
    }
}