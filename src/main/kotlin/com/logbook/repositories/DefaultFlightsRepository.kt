package com.logbook.repositories

import com.logbook.model.Flight
import com.logbook.model.Flights
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.getCollection

class DefaultFlightsRepository(val database: MongoDatabase) : FlightsRepository {
    override fun get(): Flights {
        val flightsCollection = database.getCollection<Flight>()
        return Flights(flightsCollection.find().asSequence().toList())
    }

    override fun create(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}