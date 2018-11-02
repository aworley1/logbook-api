package test_helpers

import com.logbook.model.Flight

fun createFlight(
        id: String = "defaultFlightId",
        pilotId: String = "defaultPilotId"
) = Flight(id = id, pilotId = pilotId)