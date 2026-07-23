package com.kolarc.digitaltwin.data.repository

import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus
import com.kolarc.digitaltwin.domain.model.WeldRecord
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import kotlinx.coroutines.delay

class MockMachineRepository : MachineRepository {

    private val mockList = listOf(
        MachineStatus(
            id = "1",
            location = "hol 3",
            model = "MX500",
            name = "PRES KAYNAGI",
            serialNumber = "127448",
            isOnline = true,
            lastConnected = "10/12/2025 08:07:33"
        ),
        MachineStatus(
            id = "2",
            location = "Ankara",
            model = "MX500",
            name = "Utest -3",
            serialNumber = "131131",
            isOnline = false,
            lastConnected = "07/07/2026 15:33:12"
        ),
        MachineStatus(
            id = "3",
            location = "hol 1",
            model = "MX500",
            name = "Ali Kürkçü",
            serialNumber = "127452",
            isOnline = true,
            lastConnected = "08/07/2026 08:03:01"
        ),
        MachineStatus(
            id = "4",
            location = "Ankara",
            model = "MX500",
            name = "Utest -5",
            serialNumber = "131133",
            isOnline = true,
            lastConnected = "08/07/2026 08:05:06"
        ),
        MachineStatus(
            id = "5",
            location = "Ankara",
            model = "MX500",
            name = "SUAT ARAZ",
            serialNumber = "131132",
            isOnline = true,
            lastConnected = "08/07/2026 08:28:18"
        )
    )

    private val mockMachineDetails = listOf(
        MachineDetail(
            id = "1",
            location = "hol 3",
            model = "MX500",
            name = "PRES KAYNAGI",
            serialNumber = "127448",
            isOnline = true,
            lastConnected = "10/12/2025 08:07:33",
            oeePercentage = 92.4,
            workingHours = 4821,
            lastMaintenanceDate = "01/07/2026"
        ),
        MachineDetail(
            id = "2",
            location = "Ankara",
            model = "MX500",
            name = "Utest -3",
            serialNumber = "131131",
            isOnline = false,
            lastConnected = "07/07/2026 15:33:12",
            oeePercentage = 78.1,
            workingHours = 2960,
            lastMaintenanceDate = "25/06/2026"
        ),
        MachineDetail(
            id = "3",
            location = "hol 1",
            model = "MX500",
            name = "Ali Kürkçü",
            serialNumber = "127452",
            isOnline = true,
            lastConnected = "08/07/2026 08:03:01",
            oeePercentage = 95.8,
            workingHours = 6180,
            lastMaintenanceDate = "05/07/2026"
        ),
        MachineDetail(
            id = "4",
            location = "Ankara",
            model = "MX500",
            name = "Utest -5",
            serialNumber = "131133",
            isOnline = true,
            lastConnected = "08/07/2026 08:05:06",
            oeePercentage = 89.7,
            workingHours = 4015,
            lastMaintenanceDate = "30/06/2026"
        ),
        MachineDetail(
            id = "5",
            location = "Ankara",
            model = "MX500",
            name = "SUAT ARAZ",
            serialNumber = "131132",
            isOnline = true,
            lastConnected = "08/07/2026 08:28:18",
            oeePercentage = 91.2,
            workingHours = 5234,
            lastMaintenanceDate = "03/07/2026"
        )
    )

    private val mockMigRecords = listOf(
        WeldRecord(
            "09/07/2026 12:29:33",
            "0,4",
            "hol 1",
            "127452",
            "Ali Kürkçü",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 12:29:32",
            "0,5",
            "hol 1",
            "127452",
            "Ali Kürkçü",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 12:29:30",
            "0,4",
            "hol 1",
            "127452",
            "Ali Kürkçü",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 12:29:29",
            "0,3",
            "hol 1",
            "127452",
            "Ali Kürkçü",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 12:29:28",
            "0,3",
            "hol 1",
            "127452",
            "Ali Kürkçü",
            "UTEST"
        )
    )

    private val mockMmaRecords = listOf(
        WeldRecord(
            "09/07/2026 11:45:12",
            "1,2",
            "hol 3",
            "127448",
            "PRES KAYNAGI",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 11:43:01",
            "2,5",
            "hol 3",
            "127448",
            "PRES KAYNAGI",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 11:40:55",
            "0,8",
            "hol 3",
            "127448",
            "PRES KAYNAGI",
            "UTEST"
        )
    )

    private val extraRecords = listOf(
        WeldRecord(
            "09/07/2026 10:15:00",
            "3,4",
            "Ankara",
            "131132",
            "SUAT ARAZ",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 10:12:22",
            "1,9",
            "Ankara",
            "131132",
            "SUAT ARAZ",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 10:05:11",
            "5,1",
            "Ankara",
            "131132",
            "SUAT ARAZ",
            "UTEST"
        )
    )

    override suspend fun getMachineStatus(
        machineId: String
    ): MachineStatus {
        delay(300)

        return mockList.find {
            it.id == machineId
        } ?: mockList.first()
    }

    override suspend fun getMachineDetail(
        machineId: String
    ): MachineDetail {
        delay(300)

        return mockMachineDetails.find {
            it.id == machineId
        } ?: mockMachineDetails.first()
    }

    override suspend fun getAllMachines(): List<MachineStatus> {
        return mockList
    }

    override fun getWeldRecords(
        type: String
    ): List<WeldRecord> {
        return when (type) {
            "MIG" -> mockMigRecords
            "MMA" -> mockMmaRecords
            else -> emptyList()
        }
    }

    fun getMoreWeldRecords(): List<WeldRecord> {
        return extraRecords
    }
}