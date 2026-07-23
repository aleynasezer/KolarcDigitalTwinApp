package com.kolarc.digitaltwin.data.remote

import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus

interface MachineRemoteDataSource {

    suspend fun getAllMachines(): List<MachineStatus>

    suspend fun getMachineDetail(
        machineId: String
    ): MachineDetail
}