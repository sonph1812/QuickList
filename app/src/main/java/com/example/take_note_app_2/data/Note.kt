package com.example.take_note_app_2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val color: Int, // Hex color or simple ID
    val timestamp: Long = System.currentTimeMillis(),
    val isChecklist: Boolean = false,
    val tags: String = "", // Comma separated tags
    val imageUrl: String? = null
)
