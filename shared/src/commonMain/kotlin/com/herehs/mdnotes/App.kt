package com.herehs.mdnotes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.herehs.mdnotes.domain.model.ThemeMode
import com.herehs.mdnotes.presentation.note_screen.NotesScreen
import com.herehs.mdnotes.presentation.theme.MDNotesTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val themeViewModel: ThemeViewModel = koinViewModel()
    val theme by themeViewModel.themeState.collectAsState()

    val useDarkTheme = when(theme){
        ThemeMode.DARK -> { true }
        ThemeMode.LIGHT -> { false }
        ThemeMode.SYSTEM -> {
            isSystemInDarkTheme()
        }
    }

    MDNotesTheme(darkTheme = useDarkTheme) {
        Scaffold() { paddingValues ->
            paddingValues
            NotesScreen(
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

