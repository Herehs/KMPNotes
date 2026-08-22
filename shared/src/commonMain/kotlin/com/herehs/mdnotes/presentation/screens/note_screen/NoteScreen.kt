package com.herehs.mdnotes.presentation.screens.note_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.herehs.mdnotes.domain.model.Note
import com.herehs.mdnotes.presentation.util.ScreenPreviews
import com.herehs.myapplication.simplemdtext.MDEditor
import com.herehs.myapplication.simplemdtext.MDText
import kotlin.time.Clock


@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteSave: () -> Unit = {},
    onNoteChange:(String) -> Unit,
){
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val horizontalPadding = when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> 12.dp
        WindowWidthSizeClass.MEDIUM -> 24.dp
        else -> 128.dp
    }
    if(windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT){
        SinglePaneLayout(
            note = note,
            onNoteSave = onNoteSave,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(horizontal = horizontalPadding)
        )
    } else {
        TwoPaneLayout(
            note = note,
            onNoteSave = onNoteSave,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(horizontal = horizontalPadding),
            onNoteChange = onNoteChange
        )
    }
}




@Composable
fun TwoPaneLayout(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteChange:(String) -> Unit,
    onNoteSave: () -> Unit = {}
){
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        Row(
            modifier = Modifier
                .widthIn(
                    max = 1400.dp
                )
                .fillMaxHeight()
        ) {
            VerticalDivider()
            MDText(
                rawText = note.rawText ?: "",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(all= 20.dp)
            )
            VerticalDivider()
            MDEditor(
                initialText = note.rawText ?: "",
                onTextChange = onNoteChange,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(20.dp)
            )
            VerticalDivider()
        }
    }
}

@Composable
fun SinglePaneLayout(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteSave: () -> Unit = {}
){

}

@ScreenPreviews
@Composable
fun Preview(){
    val note = Note(
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

    NoteScreen(
        note = note,
        onNoteChange = {}
    )
}