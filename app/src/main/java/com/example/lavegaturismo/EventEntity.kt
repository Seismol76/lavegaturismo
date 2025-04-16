package com.example.lavegaturismo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultural_events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val month: String,
    val day: Int,
    val description: String,
    val history: String,
    val isFavorite: Boolean = false
)