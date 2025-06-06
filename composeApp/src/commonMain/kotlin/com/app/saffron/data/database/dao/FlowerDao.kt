package com.app.saffron.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.saffron.data.models.FlowerDto

@Dao
interface FlowerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(flowers: List<FlowerDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlower(flower: FlowerDto)

    @Query("SELECT * FROM flowers")
    suspend fun getAllFlowers(): List<FlowerDto>

    @Query("SELECT * FROM flowers WHERE id = :id")
    suspend fun getFlowerById(id: Int): FlowerDto?

    @Query("DELETE FROM flowers WHERE id = :id")
    suspend fun deleteFlowerById(id: Int)

    @Query("DELETE FROM flowers")
    suspend fun deleteAllFlowers()

}