package com.logbook.repositories

import com.logbook.model.Flights

interface FlightsRepository {
    fun get(): Flights
    fun create(): String
}