package com.kolarc.digitaltwin.data.mapper

import com.kolarc.digitaltwin.data.remote.dto.MachineDetailDto
import com.kolarc.digitaltwin.domain.model.MachineDetail

fun MachineDetailDto.toDomain(): MachineDetail {
    return MachineDetail(
        id = id,
        name = name,
        model = model,
        serialNumber = serialNumber,
        location = location,
        isOnline = isOnline,
        lastConnected = lastConnected,
        oeePercentage = oeePercentage,
        workingHours = workingHours,
        lastMaintenanceDate = lastMaintenanceDate
    )
}