package com.kolarc.digitaltwin.data.remote

import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus
import kotlinx.coroutines.delay

class FakeMachineRemoteDataSource : MachineRemoteDataSource {

    private val machines = listOf(
        MachineStatus(
            id = "1",
            name = "PRES KAYNAGI",
            model = "MX500",
            serialNumber = "127448",
            location = "Hol 3",
            isOnline = true,
            lastConnected = "10/12/2025 08:07:33"
        ),
        MachineStatus(
            id = "2",
            name = "UTEST-3",
            model = "UT300",
            serialNumber = "127449",
            location = "Hol 1",
            isOnline = false,
            lastConnected = "09/12/2025 17:40:11"
        ),
        MachineStatus(
            id = "3",
            name = "ROBOT HATTI",
            model = "RB900",
            serialNumber = "127450",
            location = "Hol 2",
            isOnline = true,
            lastConnected = "10/12/2025 08:05:12"
        )
    )

    private val machineDetails = listOf(
        MachineDetail(
            id = "1",
            name = "PRES KAYNAGI",
            model = "MX500",
            serialNumber = "127448",
            location = "Hol 3",
            isOnline = true,
            lastConnected = "10/12/2025 08:07:33",
            oeePercentage = 92.4,
            workingHours = 4821,
            lastMaintenanceDate = "01/07/2026"
        ),
        MachineDetail(
            id = "2",
            name = "UTEST-3",
            model = "UT300",
            serialNumber = "127449",
            location = "Hol 1",
            isOnline = false,
            lastConnected = "09/12/2025 17:40:11",
            oeePercentage = 78.1,
            workingHours = 2960,
            lastMaintenanceDate = "25/06/2026"
        ),
        MachineDetail(
            id = "3",
            name = "ROBOT HATTI",
            model = "RB900",
            serialNumber = "127450",
            location = "Hol 2",
            isOnline = true,
            lastConnected = "10/12/2025 08:05:12",
            oeePercentage = 95.8,
            workingHours = 6180,
            lastMaintenanceDate = "05/07/2026"
        )
    )

    override suspend fun getAllMachines(): List<MachineStatus> {
        delay(500)
        return machines
    }

    override suspend fun getMachineDetail(
        machineId: String
    ): MachineDetail {
        delay(500)

        return machineDetails.firstOrNull { detail ->
            detail.id == machineId
        } ?: throw NoSuchElementException(
            "Machine detail not found: $machineId"
        )
    }
}