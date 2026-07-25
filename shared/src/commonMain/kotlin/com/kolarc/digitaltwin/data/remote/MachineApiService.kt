package com.kolarc.digitaltwin.data.remote

import com.kolarc.digitaltwin.data.remote.dto.MachineDetailDto
import com.kolarc.digitaltwin.data.remote.dto.MachineStatusDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MachineApiService(
    private val client: HttpClient
) {

    suspend fun getMachines(): List<MachineStatusDto> {
        return client
            .get("$BASE_URL/machines")
            .body()
    }

    suspend fun getMachineDetail(
        machineId: String
    ): MachineDetailDto {
        return client
            .get("$BASE_URL/machines/$machineId")
            .body()
    }

    private companion object {
        const val BASE_URL = "https://mock.kolarc.local"
    }
}