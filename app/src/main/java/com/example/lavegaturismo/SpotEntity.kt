package com.example.lavegaturismo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tourist_spots")
data class SpotEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val imageResId: Int,
    val history: String,
    val creator: String,
    val events: String,
    val funFact: String,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean = false
)