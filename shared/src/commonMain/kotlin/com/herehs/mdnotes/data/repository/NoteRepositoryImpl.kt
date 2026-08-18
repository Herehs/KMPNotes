package com.herehs.mdnotes.data.repository

import com.herehs.mdnotes.data.local.dao.NoteDao
import com.herehs.mdnotes.data.local.entity.NoteEntity
import com.herehs.mdnotes.data.mapper.toDomain
import com.herehs.mdnotes.data.mapper.toEntity
import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    override suspend fun createNote(note: Note) = dao.insert(note.toEntity())

    override suspend fun updateNote(note: Note) = dao.update(note.toEntity())

    override suspend fun deleteNote(note: Note): Long = dao.delete(note.toEntity())

    override fun getAllNotesAsFlow(): Flow<List<Note>> = dao.getAllAsFlow().map { list ->
        list.map { it.toDomain() }
    }
}