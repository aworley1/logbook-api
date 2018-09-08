package com.logbook.repositories

import com.logbook.model.Flight
import com.logbook.model.Flights
import java.util.UUID

object FlightsRepository {
    var flights = Flights(emptyList())

    fun get() = flights

    fun create(): String {
        val id = UUID.randomUUID().toString()
        flights = Flights(flights.flights + Flight(id))
        return id
    }

    fun clear() {
        flights = Flights(emptyList())
    }
}