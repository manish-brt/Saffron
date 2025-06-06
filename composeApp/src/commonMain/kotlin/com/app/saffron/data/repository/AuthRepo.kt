package com.app.saffron.data.repository

import com.app.saffron.data.models.User

interface AuthRepo {

    suspend fun login(email: String, password: String)

    suspend fun profileDetails(): User?
}