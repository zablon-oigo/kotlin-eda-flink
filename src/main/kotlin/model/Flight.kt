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
){
        fun updatePosition(elapsedSeconds: Double, speedFactor: Double = 1.0) {
        val speedInDegreesPerSecond = speed * 0.0003 * speedFactor

        val headingRadians = Math.toRadians(heading)
        val latitudeChange = speedInDegreesPerSecond * elapsedSeconds * cos(headingRadians)
        val longitudeChange = speedInDegreesPerSecond * elapsedSeconds * sin(headingRadians)

        latitude += latitudeChange
        longitude += longitudeChange

        altitude += (verticalSpeed / 60.0 * elapsedSeconds).toInt()

        timestamp = Instant.now()
        }
}