package com.kolarc.digitaltwin.data.repository

import com.kolarc.digitaltwin.domain.model.MachineStatus
import com.kolarc.digitaltwin.domain.model.WeldRecord
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import kotlinx.coroutines.delay

class MockMachineRepository : MachineRepository {

    private val mockList = listOf(
        MachineStatus("1", "hol 3", "MX500", "PRES KAYNAGI", "127448", true, "10/12/2025 08:07:33"),
        MachineStatus("2", "Ankara", "MX500", "Utest -3", "131131", false, "07/07/2026 15:33:12"),
        MachineStatus("3", "hol 1", "MX500", "Ali Kürkçü", "127452", true, "08/07/2026 08:03:01"),
        MachineStatus("4", "Ankara", "MX500", "Utest -5", "131133", true, "08/07/2026 08:05:06"),
        MachineStatus("5", "Ankara", "MX500", "SUAT ARAZ", "131132", true, "08/07/2026 08:28:18")
    )

    // MIG İlk Verileri
    private val mockMigRecords = listOf(
        WeldRecord("09/07/2026 12:29:33", "0,4", "hol 1", "127452", "Ali Kürkçü", "UTEST"),
        WeldRecord("09/07/2026 12:29:32", "0,5", "hol 1", "127452", "Ali Kürkçü", "UTEST"),
        WeldRecord("09/07/2026 12:29:30", "0,4", "hol 1", "127452", "Ali Kürkçü", "UTEST"),
        WeldRecord("09/07/2026 12:29:29", "0,3", "hol 1", "127452", "Ali Kürkçü", "UTEST"),
        WeldRecord("09/07/2026 12:29:28", "0,3", "hol 1", "127452", "Ali Kürkçü", "UTEST")
    )

    // MMA İçin İstediğin Dolu Veriler
    private val mockMmaRecords = listOf(
        WeldRecord("09/07/2026 11:45:12", "1,2", "hol 3", "127448", "PRES KAYNAGI", "UTEST"),
        WeldRecord("09/07/2026 11:43:01", "2,5", "hol 3", "127448", "PRES KAYNAGI", "UTEST"),
        WeldRecord("09/07/2026 11:40:55", "0,8", "hol 3", "127448", "PRES KAYNAGI", "UTEST")
    )

    // "Show More Results" Butonuna Basınca Gelecek Ekstra Veriler
    private val extraRecords = listOf(
        WeldRecord("09/07/2026 10:15:00", "3,4", "Ankara", "131132", "SUAT ARAZ", "UTEST"),
        WeldRecord("09/07/2026 10:12:22", "1,9", "Ankara", "131132", "SUAT ARAZ", "UTEST"),
        WeldRecord("09/07/2026 10:05:11", "5,1", "Ankara", "131132", "SUAT ARAZ", "UTEST")
    )

    override suspend fun getMachineStatus(machineId: String): MachineStatus {
        delay(300)
        return mockList.find { it.id == machineId } ?: mockList.first()
    }

    override suspend fun getAllMachines(): List<MachineStatus> {
        delay(500)
        return mockList
    }

    override fun getWeldRecords(type: String): List<WeldRecord> {
        return when (type) {
            "MIG" -> mockMigRecords
            "MMA" -> mockMmaRecords
            else -> emptyList() // TIG ve SAW boş kalmaya devam edebilir
        }
    }

    // Butonun işlevini test etmek için ek veri fırlatan fonksiyon
    fun getMoreWeldRecords(): List<WeldRecord> {
        return extraRecords
    }
}