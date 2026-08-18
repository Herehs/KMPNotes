package com.herehs.mdnotes.domain.model

data class Note(
    val id: Long,
    val title: String,
    val rawText: String,
    val createdAt: Long,
    val editedAt: Long
)
