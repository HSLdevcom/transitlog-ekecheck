package fi.hsl.ekecheck

import fi.hsl.common.config.ConfigParser
import fi.hsl.common.pulsar.PulsarApplication
import fi.hsl.common.transitdata.proto.Eke
import fi.hsl.ekecheck.WebsiteController.registerCustomerRoutes
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import mu.KotlinLogging


private val log = KotlinLogging.logger {}
fun main(args: Array<String>) {

    val server = embeddedServer(Netty, port = 8080) {
        registerCustomerRoutes()
    }
    server.start(false)
    val config = ConfigParser.createConfig()
    try {
        PulsarApplication.newInstance(config).use { app ->

            val context = app.context
            val messageHandler = MessageHandler(context)
            app.launchWithHandler(messageHandler)

        }
    } catch (e: Exception) {
        log.error("Exception at main", e)
    }
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    registerCustomerRoutes()
}