package com.logbook.repositories

import com.logbook.model.Flight
import com.logbook.model.Flights
import java.util.UUID


class StubFlightsRepository : FlightsRepository {
    var flights = Flights(emptyList())

    override fun get(pilotId: String): Flights = flights.copy(
            flights = flights.flights.filter { it.pilotId == pilotId }
    )

    override fun create(pilotId: String): String {
        //TODO generating this ID should be abstracted to a level above this class
        val id = UUID.randomUUID().toString()
        flights = Flights(flights.flights + Flight(id, pilotId))
        return id
    }

    fun clear() {
        flights = Flights(emptyList())
    }
}