package com.app.saffron.data.repository

import com.app.saffron.data.models.FlowerDto

interface FlowerRepo {

    suspend fun getFlowers(): List<FlowerDto>

    suspend fun getFlower(id: Int): FlowerDto?

}