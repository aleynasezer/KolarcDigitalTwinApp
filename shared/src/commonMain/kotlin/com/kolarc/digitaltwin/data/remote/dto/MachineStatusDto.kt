package com.kolarc.digitaltwin.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MachineStatusDto(
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
    val lastConnected: String
)