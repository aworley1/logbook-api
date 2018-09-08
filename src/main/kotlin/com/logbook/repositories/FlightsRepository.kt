package com.logbook.repositories

import com.logbook.model.Flights

object FlightsRepository {
    var flights = Flights(emptyList())

    fun get() = flights

    fun clear() {
        flights = Flights(emptyList())
    }
}