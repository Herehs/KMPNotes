package com.herehs.mdnotes.presentation.note_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    modifier: Modifier = Modifier
){
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val rows = when(windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> 2
        WindowWidthSizeClass.MEDIUM -> 3
        else -> 4
    }
    val horizontalPadding = when(windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> PaddingValues(horizontal = 20.dp)
        WindowWidthSizeClass.MEDIUM -> PaddingValues(horizontal = 50.dp)
        else -> PaddingValues(horizontal = 250.dp)
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
                """.trimIndent(),
                createdAt = Clock.System.now().toEpochMilliseconds(),
                editedAt = Clock.System.now().toEpochMilliseconds()
            )
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(rows),
        modifier = modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
        contentPadding = horizontalPadding
    ){
        items( items = notesList ) { note ->
            NoteCard(
                modifier = Modifier.padding(5.dp),
                note = note
            )
        }
    }
//    LazyColumn(
//        modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)
//    ) {
//        items( items = notesList ) { note ->
//            NoteCard(note = note)
//        }
//    }
}

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note
){
    Column(
        modifier = modifier
            .size(width = 100.dp, height = 150.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(9.dp)
    ) {
        Text(
            text = note.title ?: "",
            color = textDark,
            fontSize = 20.sp
        )
        MDText(
            rawText = note.rawText?.take(400) ?: "",
            color = textDark,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(9.dp))
                .background(color = MaterialTheme.colorScheme.background)
        )
    }
}

@ScreenPreviews
@Composable
fun NotesScreenPreview(){
    MDNotesTheme(true){
        NotesScreen()
    }
}