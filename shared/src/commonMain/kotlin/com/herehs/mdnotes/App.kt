package com.herehs.mdnotes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.presentation.screens.main_screen.MainScreen
import com.herehs.mdnotes.presentation.screens.note_screen.NoteScreen
import com.herehs.mdnotes.presentation.theme.MDNotesTheme
import com.herehs.mdnotes.presentation.util.ScreenPreviews
import kotlin.time.Clock

@ScreenPreviews
@Composable
fun App() {
//    val themeViewModel: ThemeViewModel = koinViewModel()
//    val theme by themeViewModel.themeState.collectAsState()
//
//    val useDarkTheme = when(theme){
//        ThemeMode.DARK -> { true }
//        ThemeMode.LIGHT -> { false }
//        ThemeMode.SYSTEM -> {
//            isSystemInDarkTheme()
//        }
//    }

    MDNotesTheme(darkTheme = false) {
        Scaffold() { paddingValues ->
            paddingValues
//            MainScreen(
//                modifier = Modifier.padding(paddingValues),
//                onNoteClick = {}
//            )

            var note by remember {
                mutableStateOf(
                    Note(
                        id = 1,
                        title = "title",
                        rawText = "",
                        createdAt = Clock.System.now().toEpochMilliseconds(),
                        editedAt = Clock.System.now().toEpochMilliseconds()
                    )

                )
            }

            NoteScreen(
                note = note,
                onNoteChange = {
                    note = note.copy(rawText = it)
                }
            )
        }
    }
}

