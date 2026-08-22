package com.herehs.mdnotes.domain.usecase

import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.domain.repository.NoteRepository
import com.herehs.mdnotes.util.Resource
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<Resource<List<Note>>> = repository.getAllNotesAsFlow()
}