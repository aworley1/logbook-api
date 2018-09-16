package com.logbook.ktor_modules

import com.logbook.model.FlightCreatedResponse
import com.logbook.repositories.FlightsRepository
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import org.koin.ktor.ext.inject

fun Application.pilots() {
    val flightsRepository: FlightsRepository by inject()

    routing {
        get("/pilots/{pilotId}/flights") {
            call.respond(flightsRepository.get())
        }
        post("/pilots/{pilotId}/flights") {
            call.respond(status = HttpStatusCode.Created, message = FlightCreatedResponse(flightsRepository.create()))
        }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
}