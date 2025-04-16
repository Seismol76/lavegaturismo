package com.example.lavegaturismo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {
    @Query("SELECT * FROM cultural_events")
    suspend fun getAllEvents(): List<EventEntity>

    @Insert
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("UPDATE cultural_events SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM cultural_events WHERE isFavorite = 1")
    suspend fun getFavoriteEvents(): List<EventEntity>
}