package com.herehs.myapplication.simplemdtext

fun parseBlocks(
    text: String = ""
): List<BlockNode> {
    val lines = text.lines()
    val blocks = mutableListOf<BlockNode>()
    var i = 0

    while(i < lines.size){
        val line = lines[i]

        when {
            line.isBlank() -> { i++ }
            line.startsWith("#") -> {
                val level = line.takeWhile { it == '#' }.length
                blocks.add(BlockNode.Heading(level = level, rawText = line.drop(level).trim()))
                i++
            }

            line.startsWith("```") -> {
                val language = line.removePrefix("```").trim().ifBlank { null }
                val codeLines = mutableListOf<String>()
                i++;

                while (i < lines.size && !lines[i].startsWith("```")){
                    codeLines.add(lines[i])
                    i++
                }
                i++
                blocks.add(BlockNode.CodeBlock(language = language, code = codeLines.joinToString("\n")))
            }

            line.startsWith("- ") -> {
                val items = mutableListOf<String>()

                while(i < lines.size && lines[i].startsWith("- ")){
                    items.add(lines[i].drop(2).trim())
                    i++
                }
                blocks.add(BlockNode.BulletList(items = items))
            }
            line.startsWith("* ") -> {
                val items = mutableListOf<String>()

                while(i < lines.size && lines[i].startsWith("* ")){
                    items.add(lines[i].drop(2).trim())
                    i++
                }
                blocks.add(BlockNode.BulletList(items = items))
            }
            else -> {
                val paragraphLines = mutableListOf<String>()
                while (i < lines.size && lines[i].isNotBlank() &&
                    !lines[i].startsWith("#") && !lines[i].startsWith("```") &&
                    !lines[i].startsWith("- ") && !lines[i].startsWith("* ")) {
                    paragraphLines.add(lines[i]); i++
                }
                blocks.add(BlockNode.Paragraph(paragraphLines.joinToString("\n")))
            }
        }
    }

    return blocks
}

fun parseInline(
    rawParagraph: String
): List<InlineNode>{
    var i = 0

    val nodeList = mutableListOf<InlineNode>()
    val buffer = StringBuilder()

    fun flushText() {
        if (buffer.isNotEmpty()) {
            nodeList.add(InlineNode.Text(buffer.toString()))
            buffer.clear()
        }
    }

    while (i < rawParagraph.length){
        when {
            rawParagraph.startsWith("***", i) -> {
                val end = rawParagraph.indexOf("***", startIndex = i + 3)
                if (end == -1) {
                    buffer.append(rawParagraph[i])
                    i++
                } else {
                    flushText()

                    val inner = rawParagraph.substring(i + 3, end)
                    nodeList.add(InlineNode.Bold(inner))
                    i = end + 3

                }
            }
            rawParagraph.startsWith("**", i) -> {
                val end = rawParagraph.indexOf("**", startIndex = i + 2)
                if (end == -1) {
                    buffer.append(rawParagraph[i])
                    i++
                } else {
                    flushText()

                    val inner = rawParagraph.substring(i + 2, end)
                    nodeList.add(InlineNode.Bold(inner))
                    i = end + 2

                }
            }
            rawParagraph.startsWith("*", i) -> {
                val end = rawParagraph.indexOf("*", startIndex = i + 1)
                if (end == -1) {
                    buffer.append(rawParagraph[i])
                    i++
                } else {
                    flushText()
                    val inner = rawParagraph.substring(i + 1, end)
                    nodeList.add(InlineNode.Italic(inner))
                    i = end + 1
                }
            }
            else -> {
                buffer.append(rawParagraph[i])
                i++
            }

        }

    }
    flushText()
    return nodeList
}
