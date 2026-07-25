package com.kolarc.digitaltwin.data.mapper

import com.kolarc.digitaltwin.data.remote.dto.MachineStatusDto
import com.kolarc.digitaltwin.domain.model.MachineStatus

fun MachineStatusDto.toDomain(): MachineStatus {
    return MachineStatus(
        id = id,
        name = name,
        model = model,
        serialNumber = serialNumber,
        location = location,
        isOnline = isOnline,
        lastConnected = lastConnected
    )
}
