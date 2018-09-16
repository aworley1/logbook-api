package com.logbook.repositories

import EphemeralMongo
import com.logbook.model.Flight
import com.mongodb.client.MongoDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import kotlin.test.assertEquals

class DefaultFlightsRepositoryTest {
    lateinit var defaultFlightsRepository: DefaultFlightsRepository
    lateinit var database: MongoDatabase

    @Before
    fun setup() {
        EphemeralMongo.startProcess()
        database = KMongo.createClient("localhost", 37017).getDatabase("logbook")
        defaultFlightsRepository = DefaultFlightsRepository(database)
    }

    @After
    fun tearDown() {
        EphemeralMongo.stopProcess()
    }

    @Test
    fun `should get a flight from database`() {
        //given
        val flightsCollection = database.getCollection<Flight>()
        flightsCollection.insertOne(Flight("id1"))

        //when
        val result = defaultFlightsRepository.get()

        //then
        assertEquals(1, result.flights.size)
    }

}