package com.logbook.repositories

import EphemeralMongo
import com.logbook.model.Flight
import com.mongodb.client.MongoDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.litote.kmongo.KMongo
import org.litote.kmongo.findOne
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
    fun `should get a flight for a pilot from database`() {
        //given
        val flightsCollection = database.getCollection<Flight>()
        flightsCollection.insertOne(Flight("id1", "abcdef"))
        flightsCollection.insertOne(Flight("id2", "hijklm"))

        //when
        val result = defaultFlightsRepository.get("abcdef")

        //then
        assertEquals(1, result.flights.size)
    }

    @Test
    fun `should create a flight and assign an id to it`() {
        //when
        val returnedId = defaultFlightsRepository.create("pilotId")

        //then
        assertEquals(36, returnedId.length)
        val databaseResult = database.getCollection<Flight>().findOne()
        assertEquals(returnedId, databaseResult?.id)
        assertEquals("pilotId", databaseResult?.pilotId)
    }

}