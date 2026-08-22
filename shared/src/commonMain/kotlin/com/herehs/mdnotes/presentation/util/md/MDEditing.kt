package com.herehs.myapplication.simplemdtext


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * VisualTransformation, который подсвечивает markdown прямо в редактируемом тексте:
 * символы разметки (**, *, #, -, ```) остаются на месте, но красятся отдельно от
 * содержимого, которое получает соответствующий стиль (жирный / курсив / заголовок / код).
 *
 * Ни один символ не скрывается и не удаляется — длина текста не меняется, поэтому
 * используется OffsetMapping.Identity и курсор ведёт себя предсказуемо "из коробки".
 */
data class MarkdownTextColors(
    val defaultColor: Color = Color.Unspecified,
    val syntaxColor: Color = Color.Unspecified,
    val boldColor: Color = Color.Unspecified,
    val italicColor: Color = Color.Unspecified,
    val headingColor: Color = Color.Unspecified,
    val codeColor: Color = Color.Unspecified,
    val codeBackground: Color = Color.Unspecified
)

class MarkdownVisualTransformation(
    private val defaultColor: Color,
    private val syntaxColor: Color,
    private val boldColor: Color,
    private val italicColor: Color,
    private val headingColor: Color,
    private val codeColor: Color,
    private val codeBackground: Color
) : VisualTransformation {

    private val boldRegex = Regex("(\\*\\*|__)(.+?)\\1")
    private val italicRegex = Regex(
        "(?<!\\*)\\*(?!\\*)(.+?)(?<!\\*)\\*(?!\\*)" +
                "|(?<!_)_(?!_)(.+?)(?<!_)_(?!_)"
    )
    private val headingRegex = Regex("^(#{1,6})(\\s+)(.*)$")
    private val bulletRegex = Regex("^(\\s*)([-*])(\\s+).*$")
    private val fenceRegex = Regex("^\\s*```")

    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text
        val builder = AnnotatedString.Builder(raw)
        builder.addStyle(SpanStyle(color = defaultColor), 0, raw.length)

        var isInCodeBlock = false
        var cursor = 0

        for (line in raw.split("\n")) {
            val lineEnd = cursor + line.length

            when {
                fenceRegex.containsMatchIn(line) -> {
                    isInCodeBlock = !isInCodeBlock
                    builder.addStyle(
                        SpanStyle(color = syntaxColor, fontFamily = FontFamily.Monospace),
                        cursor, lineEnd
                    )
                }
                isInCodeBlock -> {
                    builder.addStyle(
                        SpanStyle(
                            color = codeColor,
                            fontFamily = FontFamily.Monospace,
                            background = codeBackground
                        ),
                        cursor, lineEnd
                    )
                }
                headingRegex.matches(line) -> {
                    val match = headingRegex.find(line)!!
                    val markerEnd = cursor + match.groupValues[1].length
                    builder.addStyle(
                        SpanStyle(color = syntaxColor, fontWeight = FontWeight.Bold),
                        cursor, markerEnd
                    )
                    builder.addStyle(
                        SpanStyle(fontWeight = FontWeight.Bold, color = headingColor),
                        markerEnd, lineEnd
                    )
                }
                bulletRegex.matches(line) -> {
                    val match = bulletRegex.find(line)!!
                    val markerStart = cursor + match.groupValues[1].length
                    val markerEnd = markerStart + match.groupValues[2].length
                    builder.addStyle(
                        SpanStyle(color = syntaxColor, fontWeight = FontWeight.Bold),
                        markerStart, markerEnd
                    )
                    applyInlineStyles(builder, line, cursor)
                }
                else -> applyInlineStyles(builder, line, cursor)
            }

            cursor = lineEnd + 1 // +1 за символ \n
        }

        return TransformedText(builder.toAnnotatedString(), OffsetMapping.Identity)
    }

    private fun applyInlineStyles(builder: AnnotatedString.Builder, line: String, lineOffset: Int) {
        for (match in boldRegex.findAll(line)) {
            val markerLen = match.groupValues[1].length
            val start = lineOffset + match.range.first
            val end = lineOffset + match.range.last + 1
            builder.addStyle(SpanStyle(color = syntaxColor), start, start + markerLen)
            builder.addStyle(SpanStyle(color = syntaxColor), end - markerLen, end)
            builder.addStyle(
                SpanStyle(fontWeight = FontWeight.Bold, color = boldColor),
                start + markerLen, end - markerLen
            )
        }
        for (match in italicRegex.findAll(line)) {
            val start = lineOffset + match.range.first
            val end = lineOffset + match.range.last + 1
            builder.addStyle(SpanStyle(color = syntaxColor), start, start + 1)
            builder.addStyle(SpanStyle(color = syntaxColor), end - 1, end)
            builder.addStyle(
                SpanStyle(fontStyle = FontStyle.Italic, color = italicColor),
                start + 1, end - 1
            )
        }
    }
}

@Composable
fun MDEditor(
    modifier: Modifier = Modifier,
    initialText: String = "",
    onTextChange: (String) -> Unit = {},
    colors: MarkdownTextColors = MarkdownTextColors()
) {
    var fieldValue by remember { mutableStateOf(TextFieldValue(initialText)) }

    val transformation = remember {
        MarkdownVisualTransformation(
            defaultColor = colors.defaultColor,
            syntaxColor = colors.syntaxColor,
            boldColor = colors.boldColor,
            italicColor = colors.italicColor,
            headingColor = colors.headingColor,
            codeColor = colors.codeColor,
            codeBackground = colors.codeBackground
        )
    }

    BasicTextField(
        value = fieldValue,
        onValueChange = {
            fieldValue = it
            onTextChange(it.text)
        },
        visualTransformation = transformation,
        textStyle = LocalTextStyle.current,
        modifier = modifier
            .fillMaxWidth()
    )
}