package com.herehs.myapplication.simplemdtext

sealed class BlockNode {
    data class Heading(val level: Int, val rawText: String) : BlockNode()
    data class CodeBlock(val language: String?, val code: String) : BlockNode()
    data class BulletList(val items: List<String>) : BlockNode()
    data class Paragraph(val rawText: String) : BlockNode()
}