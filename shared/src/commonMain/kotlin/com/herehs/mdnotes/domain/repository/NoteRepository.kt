package com.herehs.mdnotes.domain.repository

import com.herehs.mdnotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun createNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note): Long
    suspend fun getAllNotesAsFlow(): Flow<List<Note>>
}