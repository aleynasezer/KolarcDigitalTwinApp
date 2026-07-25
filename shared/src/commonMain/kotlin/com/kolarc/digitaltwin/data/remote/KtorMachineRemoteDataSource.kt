package com.kolarc.digitaltwin.data.remote

import com.kolarc.digitaltwin.data.mapper.toDomain
import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus

class KtorMachineRemoteDataSource(
    private val apiService: MachineApiService
) : MachineRemoteDataSource {

    override suspend fun getAllMachines(): List<MachineStatus> {
        return apiService
            .getMachines()
            .map { machineDto ->
                machineDto.toDomain()
            }
    }

    override suspend fun getMachineDetail(
        machineId: String
    ): MachineDetail {
        return apiService
            .getMachineDetail(
                machineId = machineId
            )
            .toDomain()
    }
}