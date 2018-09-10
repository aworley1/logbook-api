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
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.ext.inject
import org.koin.standalone.StandAloneContext.startKoin

fun main(args: Array<String>) {
    startKoin(listOf(stubRepositories))

    val server = embeddedServer(Netty, port = 8080, module = Application::root)
    server.start(wait = true)

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