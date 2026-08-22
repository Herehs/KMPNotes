package com.herehs.mdnotes.data.repository

import com.herehs.mdnotes.data.local.dao.NoteDao
import com.herehs.mdnotes.data.local.entity.NoteEntity
import com.herehs.mdnotes.data.mapper.toDomain
import com.herehs.mdnotes.data.mapper.toEntity
import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.domain.repository.NoteRepository
import com.herehs.mdnotes.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    override suspend fun createNote(note: Note) = dao.insert(note.toEntity())

    override suspend fun updateNote(note: Note) = dao.update(note.toEntity())

    override suspend fun deleteNote(note: Note) = dao.delete(note.toEntity())

    override fun getAllNotesAsFlow(): Flow<Resource<List<Note>>> = dao.getAllAsFlow()
        .map<List<NoteEntity>, Resource<List<Note>>> { notes ->
            Resource.Success(notes.map { it.toDomain() })
        }
        .onStart { Resource.Loading(null) }
        .catch { Resource.Error(it.message ?: "Unknown error", null) }
}