package com.logbook.repositories

import com.logbook.model.Flights
import java.time.Instant

interface FlightsRepository {
    fun get(pilotId: String): Flights
    fun create(pilotId: String, departureInstant: Instant): String
}