package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.domain.model.MachineStatus
import com.kolarc.digitaltwin.domain.repository.MachineRepository

class GetMachineStatusUseCase(
    private val repository: MachineRepository
) {
    suspend fun execute(machineId: String): MachineStatus {
        return repository.getMachineStatus(machineId)
    }
}