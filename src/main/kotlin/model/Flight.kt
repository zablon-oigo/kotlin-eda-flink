package org.example.model

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
    fun toAvro(): FlightData {
        return FlightData.newBuilder()
            .setFlightId(flightId)
            .setCallsign(callsign)
            .setLatitude(latitude)
            .setLongitude(longitude)
            .setAltitude(altitude)
            .setHeading(heading)
            .setSpeed(speed)
            .setVerticalSpeed(verticalSpeed)
            .setOrigin(origin)
            .setDestination(destination)
            .setStatus(convertStatus(status))
            .setTimestamp(timestamp)
            .build()
    }

    private fun convertStatus(status: FlightStatus): AvroFlightStatus {
        return when (status) {
            FlightStatus.ON_TIME -> AvroFlightStatus.ON_TIME
            FlightStatus.DELAYED -> AvroFlightStatus.DELAYED
            FlightStatus.CANCELLED -> AvroFlightStatus.CANCELLED
            FlightStatus.DIVERTED -> AvroFlightStatus.DIVERTED
        }
    }

    companion object {
        fun createRandom(id: Int, config: dev.gamov.flightdemo.generator.config.SimulationConfig): Flight {
            val flightId = "FL$id"
            val callsign = "AIR$id"

            val latitude = Random.nextDouble(
                config.initialLatitudeMin,
                config.initialLatitudeMax
            )
            val longitude = Random.nextDouble(
                config.initialLongitudeMin,
                config.initialLongitudeMax
            )

            val altitude = Random.nextInt(
                config.initialAltitudeMin,
                config.initialAltitudeMax
            )

            val heading = Random.nextDouble(0.0, 360.0)

            val speed = Random.nextDouble(300.0, 600.0)

            val verticalSpeed = Random.nextDouble(-500.0, 500.0)

            val origins = listOf("JFK", "LAX", "ORD", "DFW", "DEN", "SEA", "SFO", "ATL", "MIA", "BOS")
            val destinations = listOf("LHR", "CDG", "FRA", "AMS", "MAD", "FCO", "ZRH", "IST", "DXB", "HKG")
            val origin = origins.random()
            val destination = destinations.random()

            val status = randomStatus(config.flightStatusProbabilities)

            return Flight(
                flightId = flightId,
                callsign = callsign,
                latitude = latitude,
                longitude = longitude,
                altitude = altitude,
                heading = heading,
                speed = speed,
                verticalSpeed = verticalSpeed,
                origin = origin,
                destination = destination,
                status = status
            )
        }
        private fun randomStatus(probabilities: Map<String, Double>): FlightStatus {
            val random = Random.nextDouble()
            var cumulativeProbability = 0.0

            for ((status, probability) in probabilities) {
                cumulativeProbability += probability
                if (random < cumulativeProbability) {
                    return FlightStatus.valueOf(status)
                }
            }
            return FlightStatus.ON_TIME
    }
}