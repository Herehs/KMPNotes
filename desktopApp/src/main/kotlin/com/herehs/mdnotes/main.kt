package com.herehs.mdnotes

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.herehs.mdnotes.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MDNotes",
    ) {
        initKoin{
            printLogger()
        }
        App()
    }
}