package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.data.repository.MockMachineRepository
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetMachineStatusUseCaseTest {

    @Test
    fun execute_yuklenen_makine_verilerini_dogru_sekilde_getirmeli() = runBlocking {
        // Given (Hazırlık)
        val mockRepository = MockMachineRepository()
        val useCase = GetMachineStatusUseCase(mockRepository)
        val testMachineId = "1"

        // When (Aksiyon)
        val result = useCase.execute(testMachineId)

        // Then (Doğrulama/Kontrol)
        assertNotNull(result)
        assertEquals("1", result.id)
        assertEquals("MX500", result.model)
        assertEquals("PRES KAYNAGI", result.name)
        assertEquals("127448", result.serialNumber)
        assertTrue(result.isOnline)
        assertEquals("10/12/2025 08:07:33", result.lastConnected)
    }
}