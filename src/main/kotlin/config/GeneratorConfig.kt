package org.example.config

import java.time.Duration

data class GeneratorConfig(
    val kafka: KafkaConfig,
    val simulation: SimulationConfig
) 


data class KafkaConfig(
    override val bootstrapServers: String,
    override val schemaRegistryUrl: String,
    val topic: String,
    @ConfigAlias("client.id")
    val clientId: String = "flight-data-generator",
    val acks: String = "all",
    val retries: Int = 3,
    @ConfigAlias("batch.size")
    val batchSize: Int = 16384,
    @ConfigAlias("linger.ms")
    val lingerMs: Long = 1,
    @ConfigAlias("buffer.memory")
    val bufferMemory: Long = 33554432,
    @ConfigAlias("security.protocol")
    val securityProtocol: String? = null,
    @ConfigAlias("sasl.mechanism")
    val saslMechanism: String? = null,
    @ConfigAlias("sasl.jaas.config")
    val saslJaasConfig: String? = null,
    @ConfigAlias("schema.registry.basic.auth.credentials.source")
    val schemaRegistryBasicAuthCredentialsSource: String? = null,
    @ConfigAlias("schema.registry.basic.auth.user.info")
    val schemaRegistryBasicAuthUserInfo: String? = null,
    @ConfigAlias("client.dns.lookup")
    val clientDnsLookup: String? = null,
    @ConfigAlias("session.timeout.ms")
    val sessionTimeoutMs: Int? = null,
    val environment: String? = null,
    @ConfigAlias("api.key")
    val apiKey: String? = null,
    @ConfigAlias("api.secret")
    val apiSecret: String? = null
) : KafkaConfigBase


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