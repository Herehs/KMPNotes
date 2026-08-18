package com.herehs.mdnotes.di

import androidx.datastore.core.okio.WebLocalStorage
import androidx.datastore.preferences.core.PreferencesSerializer
import com.herehs.mdnotes.data.local.AppDatabase
import com.herehs.mdnotes.util.createDataStore
import com.herehs.mdnotes.util.dataStoreFileName
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<AppDatabase> {
        TODO("Web driver не настроен — см. sqliteWasmWorker setup")
    }
    single {
        createDataStore(
            storage = WebLocalStorage(
                serializer = PreferencesSerializer,
                name = dataStoreFileName
            )
        )
    }
}