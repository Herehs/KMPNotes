package com.herehs.myapplication.simplemdtext

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun RenderHeading(
    headingNode: BlockNode.Heading,
    fontSize: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Unspecified,
    modifier: Modifier = Modifier
){

    val style = when(headingNode.level){
        1 -> { MaterialTheme.typography.headlineLarge }
        2 -> { MaterialTheme.typography.headlineMedium }
        3 -> { MaterialTheme.typography.headlineSmall }
        4 -> { MaterialTheme.typography.titleLarge }
        5 -> { MaterialTheme.typography.titleMedium }
        6 -> { MaterialTheme.typography.titleSmall }
        else -> { MaterialTheme.typography.titleSmall }
    }
    Text(
        text = headingNode.rawText,
        style = style,
        modifier = modifier
            .semantics { heading() },
        fontSize = fontSize,
        lineHeight = fontSize,
        color = color
    )
}

@Composable
fun RenderCodeBlock(
    codeBlockNode: BlockNode.CodeBlock,
    fontSize: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Unspecified,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerHighest),
            contentAlignment = Alignment.TopStart
        ){
            Text(
                text = if (codeBlockNode.language.isNullOrEmpty()) "" else codeBlockNode.language,
                fontSize = fontSize,
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    ),
                color = color
            )
        }
        Text(
            text = codeBlockNode.code,
            fontSize = fontSize,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp,
                    top = 5.dp,
                    end = 10.dp,
                    bottom = 10.dp
                ),
            color = color
        )
    }
}

@Composable
fun RenderParagraph(
    paragraphNode: BlockNode.Paragraph,
    fontSize: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Unspecified,
    modifier: Modifier = Modifier
){
    val inlineNodes = parseInline(paragraphNode.rawText)
    val annotatedString = buildAnnotatedString {
        for (node in inlineNodes){
            when(node){
                is InlineNode.Text -> append(node.text)
                is InlineNode.Bold -> withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                ){
                    append(node.text)
                }
                is InlineNode.Italic -> withStyle(
                    SpanStyle(
                        fontStyle = FontStyle.Italic,
                        color = color
                    )
                ){
                    append(node.text)
                }
                is InlineNode.BoldAndItalic -> withStyle(
                    SpanStyle(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                ){
                    append(node.text)
                }

            }
        }
    }

    Text(
        text = annotatedString,
        fontSize = fontSize,
        color = color,
        modifier = modifier
    )
}

@Composable
fun RenderBulletList(
    headingNode: BlockNode.BulletList,
    fontSize: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Unspecified,
    modifier: Modifier = Modifier
){
    for(item in headingNode.items){
        Text(
            text = "• $item",
            modifier = modifier,
            fontSize = fontSize,
            lineHeight = fontSize,
            color = color
        )
    }
}

@Composable
fun MDText(
    modifier: Modifier = Modifier,
    rawText: String,
    fontSize: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Unspecified,
){
    val blocks = parseBlocks(text = rawText)
    LazyColumn(
        modifier = modifier
    ) {
        items(blocks){ block ->
            when(block){
                is BlockNode.BulletList -> {
                    RenderBulletList(
                        headingNode = block,
                        fontSize = fontSize,
                        color = color
                    )
                }
                is BlockNode.CodeBlock -> {
                    RenderCodeBlock(
                        codeBlockNode = block,
                        fontSize = fontSize,
                        color = color
                    )
                }
                is BlockNode.Heading -> {
                    RenderHeading(
                        headingNode = block,
                        fontSize = fontSize,
                        color = color
                    )
                }
                is BlockNode.Paragraph -> {
                    RenderParagraph(
                        paragraphNode = block,
                        fontSize = fontSize,
                        color = color
                    )
                }
            }
        }
    }
}
