package com.herehs.mdnotes.presentation.note_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.herehs.mdnotes.presentation.util.ScreenPreviews
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotesScreen(
    notesScreenViewmodel: NoteScreenViewmodel = koinViewModel()
){
    val notesList = notesScreenViewmodel.notesList.value
    LazyColumn {
        items( items = notesList ) {

        }
    }
}

@ScreenPreviews
@Composable
fun NotesScreenPreview(){
    NotesScreen()
}