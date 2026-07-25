package com.kolarc.digitaltwin.domain.repository

import com.kolarc.digitaltwin.domain.model.WeldRecord

interface WeldRecordRepository {

    fun getWeldRecords(
        type: String
    ): List<WeldRecord>

    fun getMoreWeldRecords(): List<WeldRecord>
}