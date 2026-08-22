package com.herehs.mdnotes.presentation.screens.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.presentation.theme.MDNotesTheme
import com.herehs.mdnotes.presentation.util.ScreenPreviews
import kotlin.time.Clock

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNoteClick: (Note) -> Unit
){
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val rows = when(windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> 2
        WindowWidthSizeClass.MEDIUM -> 3
        else -> 4
    }
    val horizontalPadding = when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> 12.dp
        WindowWidthSizeClass.MEDIUM -> 24.dp
        else -> 128.dp
    }
//    val notesScreenViewmodel: NoteScreenViewmodel = koinViewModel()
//    val notesList by notesScreenViewmodel.notesList.collectAsState()
    val notesList = mutableListOf<Note>()
    repeat(50){
        notesList.add(
            Note(
                id = 1,
                title = "title",
                rawText = """
                    titletitletitletitletitletitletitletitle
                    **title**titletitletitletitletitletitletitle
                    titletitletitletitletitletitletitletitle
                    titletitletitletitletitletitletitletitle
                    titletitletitletitletitletitletitletitle
                    titletitletitletitletitletitletitletitle
                    titletitletitletitletitletitletitletitle
                    titletitletitletitletitletitletitletitle
                    titletitletitletitletitletitletitletitle
                    titletitletitletitletitletitletitletitle
                    titletitletitletitletitletitletitletitle
                """.trimIndent(),
                createdAt = Clock.System.now().toEpochMilliseconds(),
                editedAt = Clock.System.now().toEpochMilliseconds()
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(rows),
            modifier = modifier
                .fillMaxHeight()
                .widthIn(max = 1400.dp),
            contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 12.dp),
        ){
            items( items = notesList ) { note ->
                NoteCard(
                    modifier = Modifier.padding(5.dp),
                    note = note,
                    onClick = { note->
                        onNoteClick(note)
                    }
                )
            }
        }
    }
}


@ScreenPreviews
@Composable
fun NotesScreenPreview(){
    MDNotesTheme(true){
        MainScreen(
            onNoteClick = {}
        )
    }
}