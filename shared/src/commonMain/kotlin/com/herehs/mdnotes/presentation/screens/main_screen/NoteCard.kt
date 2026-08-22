package com.herehs.mdnotes.presentation.screens.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.herehs.mdnotes.domain.model.Note
import com.herehs.myapplication.simplemdtext.MDText
import kotlin.time.Clock


@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: (Note) -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(3f / 4f),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        onClick = { onClick(note) },
        border = CardDefaults.outlinedCardBorder(true)
    ) {
        Column(
            modifier = Modifier
                .padding(9.dp)
                .fillMaxSize()
        ) {
            Text(
                text = note.title ?: "",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            MDText(
                rawText = note.rawText ?: "",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun NoteCardPreview(){
    val note = Note(
        id = 1,
        title = "title",
        rawText = """
                    titletitletitletitletitletitletitletitle
                    **title**titletitletitletitletitletitletitlessssssssssshhhhhhhhhh
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

    NoteCard(
        note = note,
        onClick = {}
    )
}