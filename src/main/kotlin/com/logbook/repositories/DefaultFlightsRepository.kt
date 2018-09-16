package com.logbook.repositories

import com.logbook.model.Flight
import com.logbook.model.Flights
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.getCollection
import java.util.UUID

class DefaultFlightsRepository(database: MongoDatabase) : FlightsRepository {
    val flightsCollection = database.getCollection<Flight>()

    override fun get(): Flights {
        return Flights(flightsCollection.find().asSequence().toList())
    }

    override fun create(): String {
        //TODO generating this ID should be abstracted to a level above this class
        val id = UUID.randomUUID().toString()
        flightsCollection.insertOne(Flight(id))
        return id
    }
}