package org.example

import java.time.Instant
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


enum class FlightStatus {
    ON_TIME, DELAYED, CANCELLED, DIVERTED
}


data class Flight(
    val flightId: String,
    val callsign: String,
    var latitude: Double,
    var longitude: Double,
    var altitude: Int,
    var heading: Double,
    var speed: Double,
    var verticalSpeed: Double,
    val origin: String,
    val destination: String,
    var status: FlightStatus,
    var timestamp: Instant = Instant.now()
) 