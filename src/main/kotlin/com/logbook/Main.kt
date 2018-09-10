package com.logbook

import com.logbook.koin_modules.stubRepositories
import com.logbook.model.FlightCreatedResponse
import com.logbook.repositories.FlightsRepository
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.ext.inject
import org.koin.standalone.StandAloneContext.startKoin

fun main(args: Array<String>) {
    // Start Koin
    startKoin(listOf(stubRepositories))
    // Start Ktor
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}

fun Application.root() {
    val flightsRepository: FlightsRepository by inject()

    routing {
        get("/") {
            call.respondText("Logbook API", ContentType.Text.Plain)
        }
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