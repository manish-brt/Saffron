package com.app.saffron

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import com.app.saffron.data.models.FlowerDto
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

sealed interface Route {

    @Serializable
    data object HomeGraph: Route

    @Serializable
    data object FlowerListRoute: Route

    @Serializable
    data class FlowerDetailsRoute(val dto: String): Route

    @Serializable
    data object ProfileRoute: Route

    @Serializable
    data object AuthGraph: Route

    @Serializable
    data object LoginRoute: Route
}