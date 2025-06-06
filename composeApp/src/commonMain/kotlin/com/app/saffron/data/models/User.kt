package com.app.saffron.data.models
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


data class UserState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

@Serializable
data class User(
    @SerialName("bio")
    val bio: String,

    @SerialName("dob")
    val dob: String,

    @SerialName("gender")
    val gender: String,

    @SerialName("name")
    val name: String,

    @SerialName("profileImageUrl")
    val profileImageUrl: String,

    @SerialName("username")
    val username: String
)

