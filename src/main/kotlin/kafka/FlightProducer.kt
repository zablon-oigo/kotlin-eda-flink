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
    

}