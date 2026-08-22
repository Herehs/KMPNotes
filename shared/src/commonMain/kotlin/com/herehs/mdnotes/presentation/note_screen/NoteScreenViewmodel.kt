package com.herehs.mdnotes.presentation.note_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.domain.usecase.GetAllNotesUseCase
import com.herehs.mdnotes.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.listOf

class NoteScreenViewmodel(
    getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {
    val notesList: StateFlow<Resource<List<Note>>> = getAllNotesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Resource.Loading(null)
    )
}
