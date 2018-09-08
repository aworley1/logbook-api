package com.logbook.model

data class Flights(val flights: List<Flight>)

data class Flight(val id: String)

data class FlightCreatedResponse(val id: String)