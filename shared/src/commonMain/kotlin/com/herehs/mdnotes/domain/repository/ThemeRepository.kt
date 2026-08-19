package com.herehs.mdnotes.domain.repository

import com.herehs.mdnotes.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    val currentTheme: Flow<ThemeMode>

    suspend fun setTheme(theme: ThemeMode)
}