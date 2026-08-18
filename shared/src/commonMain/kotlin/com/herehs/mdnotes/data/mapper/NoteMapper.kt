package com.herehs.mdnotes.data.mapper

import com.herehs.mdnotes.data.local.entity.NoteEntity
import com.herehs.mdnotes.domain.model.Note

fun NoteEntity.toDomain(): Note = Note(
    id = id,
    title = title,
    rawText = rawText,
    createdAt = createdAt,
    editedAt = editedAt
)

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    title = title,
    rawText = rawText,
    createdAt = createdAt,
    editedAt = editedAt
)