package com.logbook.repositories

import com.logbook.model.Flight
import com.logbook.model.Flights
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection
import java.time.Instant
import java.util.UUID

class DefaultFlightsRepository(database: MongoDatabase) : FlightsRepository {
    val flightsCollection = database.getCollection<Flight>()

    override fun get(pilotId: String): Flights {
        return Flights(flightsCollection.find(Flight::pilotId eq pilotId).asSequence().toList())
    }

    override fun create(pilotId: String, departureInstant: Instant): String {
        //TODO generating this ID should be abstracted to a level above this class
        val id = UUID.randomUUID().toString()
        flightsCollection.insertOne(Flight(id, pilotId, departureInstant))
        return id
    }
}