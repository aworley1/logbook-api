package com.logbook.ktor_modules

import com.logbook.model.CreateFlightRequest
import com.logbook.model.FlightCreatedResponse
import com.logbook.repositories.FlightsRepository
import com.logbook.type_adapters.InstantDeserializer
import com.logbook.type_adapters.InstantSerializer
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import org.koin.ktor.ext.inject
import java.time.Instant

fun Application.pilots() {
    val flightsRepository: FlightsRepository by inject()

    routing {
        get("/pilots/{pilotId}/flights") {
            val pilotId = call.parameters["pilotId"]!!
            call.respond(flightsRepository.get(pilotId))
        }
        post("/pilots/{pilotId}/flights") {
            val pilotId = call.parameters["pilotId"]!!
            val departureInstant = call.receive<CreateFlightRequest>().departureInstant
            call.respond(status = HttpStatusCode.Created, message = FlightCreatedResponse(flightsRepository.create(pilotId, Instant.parse(departureInstant))))
        }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            registerTypeAdapter(Instant::class.java, InstantSerializer)
            registerTypeAdapter(Instant::class.java, InstantDeserializer)
        }
    }
}