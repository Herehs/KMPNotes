package com.herehs.myapplication.simplemdtext

sealed class InlineNode {
    data class Text(val text: String) : InlineNode()
    data class Bold(val text: String) : InlineNode()
    data class Italic(val text: String) : InlineNode()
    data class BoldAndItalic(val text: String) : InlineNode()
}