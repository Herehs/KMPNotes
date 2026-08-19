package com.herehs.mdnotes.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String?,
    val rawText: String?,
    val createdAt: Long,
    val editedAt: Long
)
