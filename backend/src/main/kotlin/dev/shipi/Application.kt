package dev.shipi

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.shipi.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSwagger()
    configureAuthorization(listOf("/swagger", "/swagger/documentation.yaml"))
    configureSerialization()
    configureRouting()
}
