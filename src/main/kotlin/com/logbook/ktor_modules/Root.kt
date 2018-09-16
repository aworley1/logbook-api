package com.logbook.ktor_modules

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.root() {
    routing {
        get("/") {
            call.respondText("Logbook API", ContentType.Text.Plain)
        }
    }
}