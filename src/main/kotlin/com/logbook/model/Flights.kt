package com.logbook.model

import java.time.Instant

data class Flights(val flights: List<Flight>)

data class Flight(val id: String, val pilotId: String, val departureInstant: Instant)

data class FlightCreatedResponse(val id: String)

data class CreateFlightRequest(val departureInstant: String)