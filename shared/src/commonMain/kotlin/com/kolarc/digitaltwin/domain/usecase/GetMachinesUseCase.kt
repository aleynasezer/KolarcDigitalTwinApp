package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.domain.model.MachineStatus
import com.kolarc.digitaltwin.domain.repository.MachineRepository

class GetMachinesUseCase(
    private val repository: MachineRepository
) {

    suspend operator fun invoke(): List<MachineStatus> {
        return repository.getAllMachines()
    }
}