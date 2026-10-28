package org.example.kafka


import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import java.util.Properties
import java.util.concurrent.atomic.AtomicLong


class FlightProducer(
    private val config: KafkaConfig,
    private val producer: KafkaProducer<String, FlightData>? = null
) {

    private val logger = LoggerFactory.getLogger(FlightProducer::class.java)
    private val kafkaProducer: KafkaProducer<String, FlightData>
    private val messageCounter = AtomicLong(0)
    private var running = false


    init {
        kafkaProducer = producer ?: createProducer()
        logger.info("Kafka producer initialized with bootstrap servers: ${config.bootstrapServers}")
        logger.info("Schema Registry URL: ${config.schemaRegistryUrl}")
    }
        private fun createProducer(): KafkaProducer<String, FlightData> {
        val props = Properties().apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.bootstrapServers)
            put(ProducerConfig.CLIENT_ID_CONFIG, config.clientId)
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer::class.java.name)
            put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, config.schemaRegistryUrl)
            put(ProducerConfig.ACKS_CONFIG, config.acks)
            put(ProducerConfig.RETRIES_CONFIG, config.retries)
            put(ProducerConfig.BATCH_SIZE_CONFIG, config.batchSize)
            put(ProducerConfig.LINGER_MS_CONFIG, config.lingerMs)
            put(ProducerConfig.BUFFER_MEMORY_CONFIG, config.bufferMemory)



            return KafkaProducer(props)

        }
    
        fun start(flightFlow: Flow<Flight>, scope: CoroutineScope) {
            if (running) {
                logger.warn("Flight producer is already running")
                return
            }

            running = true
            logger.info("Starting flight producer to topic: ${config.topic}")

            scope.launch(Dispatchers.IO) {
                flightFlow.collect { flight ->
                    sendFlight(flight)
                }
            }
        }

        fun stop() {
        running = false
        kafkaProducer.close()
        logger.info("Flight producer stopped after sending ${messageCounter.get()} messages")
    }
}