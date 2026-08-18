package com.herehs.mdnotes.di

import com.herehs.mdnotes.data.local.AppDatabase
import com.herehs.mdnotes.data.repository.NoteRepositoryImpl
import com.herehs.mdnotes.domain.repository.NoteRepository
import com.herehs.mdnotes.domain.usecase.GetAllNotesUseCase
import com.herehs.mdnotes.presentation.note_screen.NoteScreenViewmodel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val domainModule = module {
    single { GetAllNotesUseCase(get()) }
}
val dataModule = module {
    single { get<AppDatabase>().noteDao() }
    single<NoteRepository> { NoteRepositoryImpl(get()) }
}

val presentationModule = module {
    viewModelOf(::NoteScreenViewmodel)
}

expect val platformModule: Module
