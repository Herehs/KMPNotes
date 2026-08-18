package com.herehs.mdnotes.presentation.note_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.domain.usecase.GetAllNotesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NoteScreenViewmodel(
    getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {
    val notesList: StateFlow<List<Note>> = getAllNotesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )
}
