package com.herehs.mdnotes.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.FileStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesFileSerializer
import androidx.datastore.preferences.core.PreferencesSerializer
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.herehs.mdnotes.data.local.AppDatabase
import com.herehs.mdnotes.util.createDataStore
import com.herehs.mdnotes.util.dataStoreFileName
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val platformModule: Module = module {
    single<AppDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "notes.db")
        Room.databaseBuilder<AppDatabase>(dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
    single<DataStore<Preferences>> {
        createDataStore(
            storage = FileStorage(
                serializer = PreferencesFileSerializer,
                produceFile = { File(System.getProperty("java.io.tmpdir"), dataStoreFileName) }
            )
        )
    }
}