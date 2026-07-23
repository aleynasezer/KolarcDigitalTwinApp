package com.kolarc.digitaltwin.data.remote

import com.kolarc.digitaltwin.data.dto.MachineDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MachineApiService(
    private val client: HttpClient
) {

    suspend fun getMachines(): List<MachineDto> {
        return client
            .get("https://mock.kolarc.local/machines")
            .body()
    }
}