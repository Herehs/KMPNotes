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
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<AppDatabase> {
        val context = androidContext()
        val dbFile = context.getDatabasePath("notes.db")
        Room.databaseBuilder<AppDatabase>(context.applicationContext, dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
    single<DataStore<Preferences>> {
        val context = androidContext()
        createDataStore(
            storage = FileStorage<Preferences>(
                serializer = PreferencesFileSerializer,
                produceFile = { context.filesDir.resolve(dataStoreFileName) }
            )
        )
    }

}