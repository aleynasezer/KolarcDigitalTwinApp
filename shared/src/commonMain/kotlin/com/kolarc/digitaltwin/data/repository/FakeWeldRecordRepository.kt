package com.kolarc.digitaltwin.data.repository

import com.kolarc.digitaltwin.domain.model.WeldRecord
import com.kolarc.digitaltwin.domain.repository.WeldRecordRepository

class FakeWeldRecordRepository : WeldRecordRepository {

    private val mockMigRecords = listOf(
        WeldRecord(
            "09/07/2026 12:29:33",
            "0,4",
            "hol 1",
            "127452",
            "Ali Kürkçü",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 12:29:32",
            "0,5",
            "hol 1",
            "127452",
            "Ali Kürkçü",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 12:29:30",
            "0,4",
            "hol 1",
            "127452",
            "Ali Kürkçü",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 12:29:29",
            "0,3",
            "hol 1",
            "127452",
            "Ali Kürkçü",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 12:29:28",
            "0,3",
            "hol 1",
            "127452",
            "Ali Kürkçü",
            "UTEST"
        )
    )

    private val mockMmaRecords = listOf(
        WeldRecord(
            "09/07/2026 11:45:12",
            "1,2",
            "hol 3",
            "127448",
            "PRES KAYNAGI",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 11:43:01",
            "2,5",
            "hol 3",
            "127448",
            "PRES KAYNAGI",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 11:40:55",
            "0,8",
            "hol 3",
            "127448",
            "PRES KAYNAGI",
            "UTEST"
        )
    )

    private val extraRecords = listOf(
        WeldRecord(
            "09/07/2026 10:15:00",
            "3,4",
            "Ankara",
            "131132",
            "SUAT ARAZ",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 10:12:22",
            "1,9",
            "Ankara",
            "131132",
            "SUAT ARAZ",
            "UTEST"
        ),
        WeldRecord(
            "09/07/2026 10:05:11",
            "5,1",
            "Ankara",
            "131132",
            "SUAT ARAZ",
            "UTEST"
        )
    )

    override fun getWeldRecords(
        type: String
    ): List<WeldRecord> {
        return when (type) {
            "MIG" -> mockMigRecords
            "MMA" -> mockMmaRecords
            else -> emptyList()
        }
    }

    override fun getMoreWeldRecords(): List<WeldRecord> {
        return extraRecords
    }
}