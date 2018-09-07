package com.logbook

import com.logbook.model.Flights
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, port = 8080, module = Application::root)
    server.start(wait = true)

}

fun Application.root() {
    routing {
        get("/") {
            call.respondText("Logbook API", ContentType.Text.Plain)
        }
        get("/pilot/{pilotId}/flights") {
            call.respond(Flights(emptyList()))
        }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
}