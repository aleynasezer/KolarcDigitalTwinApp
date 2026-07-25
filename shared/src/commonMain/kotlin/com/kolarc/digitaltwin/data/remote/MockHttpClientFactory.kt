package com.kolarc.digitaltwin.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createMockHttpClient(): HttpClient {
    val mockEngine = MockEngine { request ->
        when {
            request.url.encodedPath == "/machines" -> {
                respondJson(
                    content = MACHINES_JSON,
                    status = HttpStatusCode.OK
                )
            }

            request.url.encodedPath.startsWith("/machines/") -> {
                val machineId = request.url.encodedPath
                    .substringAfterLast("/")

                val machineDetailJson = MACHINE_DETAILS_JSON[machineId]

                if (machineDetailJson != null) {
                    respondJson(
                        content = machineDetailJson,
                        status = HttpStatusCode.OK
                    )
                } else {
                    respondJson(
                        content = """{"message":"Machine not found"}""",
                        status = HttpStatusCode.NotFound
                    )
                }
            }

            else -> {
                respondJson(
                    content = """{"message":"Not found"}""",
                    status = HttpStatusCode.NotFound
                )
            }
        }
    }

    return HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }
}

private fun io.ktor.client.engine.mock.MockRequestHandleScope.respondJson(
    content: String,
    status: HttpStatusCode
) = respond(
    content = content,
    status = status,
    headers = headersOf(
        HttpHeaders.ContentType,
        "application/json"
    )
)

private val MACHINE_DETAILS_JSON = mapOf(
    "1" to """
        {
          "id": "1",
          "name": "PRES KAYNAGI",
          "model": "MX500",
          "serial_number": "127448",
          "location": "hol 3",
          "is_online": true,
          "last_connected": "10/12/2025 08:07:33",
          "oee_percentage": 92.4,
          "working_hours": 4821,
          "last_maintenance_date": "01/07/2026"
        }
    """.trimIndent(),
    "2" to """
        {
          "id": "2",
          "name": "Utest -3",
          "model": "MX500",
          "serial_number": "131131",
          "location": "Ankara",
          "is_online": false,
          "last_connected": "07/07/2026 15:33:12",
          "oee_percentage": 78.1,
          "working_hours": 2960,
          "last_maintenance_date": "25/06/2026"
        }
    """.trimIndent(),
    "3" to """
        {
          "id": "3",
          "name": "Ali Kürkçü",
          "model": "MX500",
          "serial_number": "127452",
          "location": "hol 1",
          "is_online": true,
          "last_connected": "08/07/2026 08:03:01",
          "oee_percentage": 95.8,
          "working_hours": 6180,
          "last_maintenance_date": "05/07/2026"
        }
    """.trimIndent(),
    "4" to """
        {
          "id": "4",
          "name": "Utest -5",
          "model": "MX500",
          "serial_number": "131133",
          "location": "Ankara",
          "is_online": true,
          "last_connected": "08/07/2026 08:05:06",
          "oee_percentage": 89.7,
          "working_hours": 4015,
          "last_maintenance_date": "30/06/2026"
        }
    """.trimIndent(),
    "5" to """
        {
          "id": "5",
          "name": "SUAT ARAZ",
          "model": "MX500",
          "serial_number": "131132",
          "location": "Ankara",
          "is_online": true,
          "last_connected": "08/07/2026 08:28:18",
          "oee_percentage": 91.2,
          "working_hours": 5234,
          "last_maintenance_date": "03/07/2026"
        }
    """.trimIndent()
)

private const val MACHINES_JSON = """
[
  {
    "id": "1",
    "location": "hol 3",
    "model": "MX500",
    "name": "PRES KAYNAGI",
    "serial_number": "127448",
    "is_online": true,
    "last_connected": "10/12/2025 08:07:33"
  },
  {
    "id": "2",
    "location": "Ankara",
    "model": "MX500",
    "name": "Utest -3",
    "serial_number": "131131",
    "is_online": false,
    "last_connected": "07/07/2026 15:33:12"
  },
  {
    "id": "3",
    "location": "hol 1",
    "model": "MX500",
    "name": "Ali Kürkçü",
    "serial_number": "127452",
    "is_online": true,
    "last_connected": "08/07/2026 08:03:01"
  },
  {
    "id": "4",
    "location": "Ankara",
    "model": "MX500",
    "name": "Utest -5",
    "serial_number": "131133",
    "is_online": true,
    "last_connected": "08/07/2026 08:05:06"
  },
  {
    "id": "5",
    "location": "Ankara",
    "model": "MX500",
    "name": "SUAT ARAZ",
    "serial_number": "131132",
    "is_online": true,
    "last_connected": "08/07/2026 08:28:18"
  }
]
"""