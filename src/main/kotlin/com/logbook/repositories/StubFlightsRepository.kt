package com.logbook.repositories

import com.logbook.model.Flight
import com.logbook.model.Flights
import java.util.UUID


interface FlightsRepository {
    fun get(): Flights
    fun create(): String
}

class StubFlightsRepository : FlightsRepository {
    var flights = Flights(emptyList())

    override fun get() = flights

    override fun create(): String {
        val id = UUID.randomUUID().toString()
        flights = Flights(flights.flights + Flight(id))
        return id
    }

    fun clear() {
        flights = Flights(emptyList())
    }
}