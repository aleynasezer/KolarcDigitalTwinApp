package com.kolarc.digitaltwin.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MachineDto(
    val id: String,
    val location: String,
    val model: String,
    val name: String,
    @SerialName("serial_number")
    val serialNumber: String,
    @SerialName("is_online")
    val isOnline: Boolean,
    @SerialName("last_connected")
    val lastConnected: String
)