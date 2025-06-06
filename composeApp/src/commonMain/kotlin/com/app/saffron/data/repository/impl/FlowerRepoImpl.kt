package com.app.saffron.data.repository.impl

import com.app.saffron.data.database.AppDatabase
import com.app.saffron.data.database.dao.FlowerDao
import com.app.saffron.data.models.FlowerDto
import com.app.saffron.data.models.FlowerListResponse
import com.app.saffron.data.repository.FlowerRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val BASE_URL = "https://f6f1aa1f-4460-40f2-aa9d-4cda66816485.mock.pstmn.io"

class FlowerRepoImpl(
    private val httpClient: HttpClient,
//    private val flowerDao: FlowerDao
) : FlowerRepo {

    override suspend fun getFlowers(): List<FlowerDto> {
        val res = try {
            val response = httpClient.get("$BASE_URL/flowers/list")
            val list = response.body<FlowerListResponse>().list
//            appDatabase.flowerDao().insertAll(list)
            list
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }
        return res
    }

    override suspend fun getFlower(id: Int): FlowerDto? {
//        val res = try {
//            val response = httpClient.get("$BASE_URL/flowers/$id")
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return emptyList()
//        }

        return null
    }
}