package com.app.saffron.data.repository.impl

import com.app.saffron.data.models.FlowerListResponse
import com.app.saffron.data.models.User
import com.app.saffron.data.repository.AuthRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val BASE_URL = "https://f6f1aa1f-4460-40f2-aa9d-4cda66816485.mock.pstmn.io"

class AuthRepoImpl(
    private val httpClient: HttpClient
): AuthRepo {

    override suspend fun login(email: String, password: String) {
        //later
    }

    override suspend fun profileDetails(): User? {
        val res = try {
            val response = httpClient.get("$BASE_URL/profile")
            response.body<User>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return res
    }
}