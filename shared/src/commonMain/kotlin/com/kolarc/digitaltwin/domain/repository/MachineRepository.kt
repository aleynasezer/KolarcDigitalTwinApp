package com.kolarc.digitaltwin.domain.repository

import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus
import com.kolarc.digitaltwin.domain.model.WeldRecord

interface MachineRepository {

    suspend fun getMachineStatus(
        machineId: String
    ): MachineStatus

    suspend fun getMachineDetail(
        machineId: String
    ): MachineDetail

    suspend fun getAllMachines(): List<MachineStatus>

    fun getWeldRecords(
        type: String
    ): List<WeldRecord>
}