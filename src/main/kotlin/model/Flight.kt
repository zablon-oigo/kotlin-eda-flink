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


        fun randomizeMovement() {
        heading = (heading + Random.nextDouble(-5.0, 5.0)) % 360.0
        if (heading < 0) heading += 360.0

        speed = (speed + Random.nextDouble(-10.0, 10.0)).coerceIn(300.0, 600.0)

        verticalSpeed = (verticalSpeed + Random.nextDouble(-100.0, 100.0)).coerceIn(-1000.0, 1000.0)

        when {
            altitude > 40000 -> verticalSpeed = -500.0
            altitude < 30000 -> verticalSpeed = 500.0
        }
    }
        fun toMap(): Map<String, Any> {
        return mapOf(
            "flightId" to flightId,
            "callsign" to callsign,
            "latitude" to latitude,
            "longitude" to longitude,
            "altitude" to altitude,
            "heading" to heading,
            "speed" to speed,
            "verticalSpeed" to verticalSpeed,
            "origin" to origin,
            "destination" to destination,
            "status" to status.name,
            "timestamp" to timestamp.toEpochMilli()
        )
    }
}