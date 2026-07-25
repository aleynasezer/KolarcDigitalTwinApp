package com.kolarc.digitaltwin.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MachineDetailDto(

    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("model")
    val model: String,

    @SerialName("serial_number")
    val serialNumber: String,

    @SerialName("location")
    val location: String,

    @SerialName("is_online")
    val isOnline: Boolean,

    @SerialName("last_connected")
    val lastConnected: String,

    @SerialName("oee_percentage")
    val oeePercentage: Double,

    @SerialName("working_hours")
    val workingHours: Int,

    @SerialName("last_maintenance_date")
    val lastMaintenanceDate: String
)