package com.herehs.mdnotes.di

import com.herehs.mdnotes.data.local.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<AppDatabase> {
        TODO("Web driver не настроен — см. sqliteWasmWorker setup")
    }
}