package com.herehs.mdnotes.domain.model

data class Note(
    val id: Long,
    var title: String?,
    var rawText: String?,
    val createdAt: Long,
    var editedAt: Long
)

