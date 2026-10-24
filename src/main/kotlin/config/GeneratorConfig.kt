package org.example.config

import java.time.Duration

data class GeneratorConfig(
    val kafka: KafkaConfig,
    val simulation: SimulationConfig
) 

data class SimulationConfig(
    val numFlights: Int = 100,
    val updateInterval: Duration = Duration.ofSeconds(1),
    val speedFactor: Double = 1.0,
    val initialAltitudeMin: Int = 30000,
    val initialAltitudeMax: Int = 40000,
    val initialLatitudeMin: Double = 25.0,
    val initialLatitudeMax: Double = 50.0,
    val initialLongitudeMin: Double = -125.0,
    val initialLongitudeMax: Double = -70.0,
    val flightStatusProbabilities: Map<String, Double> = mapOf(
        "ON_TIME" to 0.7,
        "DELAYED" to 0.2,
        "CANCELLED" to 0.05,
        "DIVERTED" to 0.05
    )
)