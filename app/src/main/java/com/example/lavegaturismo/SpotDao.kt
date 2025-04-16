package com.example.lavegaturismo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SpotDao {
    @Query("SELECT * FROM tourist_spots")
    suspend fun getAllSpots(): List<SpotEntity>

    @Insert
    suspend fun insertSpots(spots: List<SpotEntity>)

    @Query("UPDATE tourist_spots SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM tourist_spots WHERE isFavorite = 1")
    suspend fun getFavoriteSpots(): List<SpotEntity>
}