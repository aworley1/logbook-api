package com.logbook

import com.logbook.koin_modules.stubRepositories
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.standalone.StandAloneContext.startKoin

fun main(args: Array<String>) {
    // Start Koin
    startKoin(listOf(stubRepositories))
    // Start Ktor
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}
