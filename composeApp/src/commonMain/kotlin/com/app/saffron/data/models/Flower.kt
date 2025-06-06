package com.app.saffron.data.models

import androidx.room.Entity
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

data class FlowerListState(
    val flowers: List<FlowerDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedIdx: Int = 0
)

@Serializable
data class FlowerListResponse(
    @SerialName("list")
    val list: List<FlowerDto> = emptyList()
)

@Serializable
@Entity(tableName = "flowers")
data class FlowerDto(
    @SerialName("id")
    val id: Int,

    @SerialName("bloom_time")
    val bloomTime: String,

    @SerialName("common_colors")
    val commonColors: List<String>,

    @SerialName("description")
    val description: String,

    @SerialName("family")
    val family: String,

    @SerialName("free_image_urls")
    val freeImageUrls: List<String>,

    @SerialName("native_region")
    val nativeRegion: String,

    @SerialName("scientific_name")
    val scientificName: String,

    @SerialName("subtitle")
    val subtitle: String,

    @SerialName("title")
    val title: String,

    @SerialName("uses")
    val uses: List<String>
)

