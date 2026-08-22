package com.herehs.mdnotes.presentation.note_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowWidthSizeClass
import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.presentation.theme.MDNotesTheme
import com.herehs.mdnotes.presentation.theme.textDark
import com.herehs.mdnotes.presentation.util.ScreenPreviews
import com.herehs.myapplication.simplemdtext.MDText
import kotlin.time.Clock

@Composable
fun NotesScreen(
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
        NotesScreen(
            onNoteClick = {}
        )
    }
}