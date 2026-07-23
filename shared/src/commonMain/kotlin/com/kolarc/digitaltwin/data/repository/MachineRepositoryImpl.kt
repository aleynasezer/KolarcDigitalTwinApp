package com.kolarc.digitaltwin.data.repository

import com.kolarc.digitaltwin.data.remote.MachineRemoteDataSource
import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus
import com.kolarc.digitaltwin.domain.model.WeldRecord
import com.kolarc.digitaltwin.domain.repository.MachineRepository

class MachineRepositoryImpl(
    private val remoteDataSource: MachineRemoteDataSource
) : MachineRepository {

    override suspend fun getMachineStatus(
        machineId: String
    ): MachineStatus {
        return remoteDataSource
            .getAllMachines()
            .firstOrNull { machine ->
                machine.id == machineId
            }
            ?: throw NoSuchElementException(
                "Machine not found: $machineId"
            )
    }

    override suspend fun getMachineDetail(
        machineId: String
    ): MachineDetail {
        return remoteDataSource.getMachineDetail(
            machineId = machineId
        )
    }

    override suspend fun getAllMachines(): List<MachineStatus> {
        return remoteDataSource.getAllMachines()
    }

    override fun getWeldRecords(
        type: String
    ): List<WeldRecord> {
        return emptyList()
    }
}