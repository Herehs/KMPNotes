package com.herehs.mdnotes.domain.repository

import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.util.Resource
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun createNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun getAllNotesAsFlow(): Flow<Resource<List<Note>>>
}