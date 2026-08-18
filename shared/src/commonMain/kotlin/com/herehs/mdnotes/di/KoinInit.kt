package com.herehs.mdnotes.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(config: KoinAppDeclaration? = null): KoinApplication {
    return startKoin {
        includes(config)
        modules(
            domainModule,
            dataModule,
            presentationModule,
            platformModule
        )
    }
}