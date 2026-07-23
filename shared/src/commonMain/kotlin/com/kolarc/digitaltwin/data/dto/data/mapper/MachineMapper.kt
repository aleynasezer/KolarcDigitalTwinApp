package com.kolarc.digitaltwin.data.mapper

import com.kolarc.digitaltwin.data.dto.MachineDto
import com.kolarc.digitaltwin.domain.model.MachineStatus

fun MachineDto.toDomain(): MachineStatus {
    return MachineStatus(
        id = id,
        location = location,
        model = model,
        name = name,
        serialNumber = serialNumber,
        isOnline = isOnline,
        lastConnected = lastConnected
    )
}