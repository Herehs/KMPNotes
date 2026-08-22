package com.herehs.mdnotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herehs.mdnotes.domain.model.ThemeMode
import com.herehs.mdnotes.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val themeRepository: ThemeRepository
) : ViewModel() {
    val themeState = themeRepository.currentTheme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ThemeMode.LIGHT
    )
    fun setTheme(mode: ThemeMode){
        viewModelScope.launch { themeRepository.setTheme(mode) }
    }
}