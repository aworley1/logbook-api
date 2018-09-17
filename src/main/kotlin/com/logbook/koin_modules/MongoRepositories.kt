package com.logbook.koin_modules

import com.logbook.databases.LogbookDatabase
import com.logbook.repositories.DefaultFlightsRepository
import com.logbook.repositories.FlightsRepository
import org.koin.dsl.module.module

val mongoRepositories = module {
    single { LogbookDatabase() }
    single<FlightsRepository> { DefaultFlightsRepository(LogbookDatabase().database) }
}