package com.logbook.repositories

import com.logbook.model.Flights

interface FlightsRepository {
    fun get(pilotId: String): Flights
    fun create(pilotId: String): String
}