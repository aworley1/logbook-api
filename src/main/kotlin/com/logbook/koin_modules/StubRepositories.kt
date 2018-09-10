package com.logbook.koin_modules

import com.logbook.repositories.FlightsRepository
import com.logbook.repositories.StubFlightsRepository
import org.koin.dsl.module.module

val stubRepositories = module {
    single<StubFlightsRepository> { StubFlightsRepository() } bind FlightsRepository::class
}