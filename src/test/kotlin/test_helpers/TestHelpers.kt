package test_helpers

import com.logbook.model.Flight
import java.time.Instant

fun createFlight(
        id: String = "defaultFlightId",
        pilotId: String = "defaultPilotId",
        departureInstant: Instant = Instant.parse("1970-04-27T00:00:00Z")
) = Flight(id = id, pilotId = pilotId, departureInstant = departureInstant)