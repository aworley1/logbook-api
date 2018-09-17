package com.logbook.databases

import com.mongodb.client.MongoDatabase
import com.typesafe.config.ConfigFactory
import io.ktor.config.HoconApplicationConfig
import org.litote.kmongo.KMongo

class LogbookDatabase {
    val config = HoconApplicationConfig(ConfigFactory.load())
    val database: MongoDatabase = KMongo.createClient(
            host = config.property("database.hostname").getString(),
            port = config.property("database.port").getString().toInt()
    ).getDatabase(config.property("database.databaseName").getString())
}