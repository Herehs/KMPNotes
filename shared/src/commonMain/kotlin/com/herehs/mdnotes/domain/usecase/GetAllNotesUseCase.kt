package com.herehs.mdnotes.domain.usecase

import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> = repository.getAllNotesAsFlow()
}