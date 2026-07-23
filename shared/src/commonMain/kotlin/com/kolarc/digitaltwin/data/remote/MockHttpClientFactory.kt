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
        when (request.url.encodedPath) {
            "/machines" -> {
                respond(
                    content = MACHINES_JSON,
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        "application/json"
                    )
                )
            }

            else -> {
                respond(
                    content = """{"message":"Not found"}""",
                    status = HttpStatusCode.NotFound,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        "application/json"
                    )
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
