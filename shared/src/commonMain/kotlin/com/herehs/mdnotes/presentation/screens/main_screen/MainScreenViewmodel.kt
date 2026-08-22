package com.herehs.mdnotes.presentation.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.domain.usecase.GetAllNotesUseCase
import com.herehs.mdnotes.util.Resource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainScreenViewmodel(
    getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {
    val notesList: StateFlow<Resource<List<Note>>> = getAllNotesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Resource.Loading(null)
    )
}
