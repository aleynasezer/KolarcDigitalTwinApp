package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.repository.MachineRepository

class GetMachineDetailUseCase(
    private val repository: MachineRepository
) {

    suspend operator fun invoke(
        machineId: String
    ): MachineDetail {
        return repository.getMachineDetail(machineId)
    }
}
