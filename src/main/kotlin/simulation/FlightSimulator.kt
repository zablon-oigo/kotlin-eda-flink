package org.example



import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.Instant
import kotlin.random.Random



class FlightSimulator(
    private val config: SimulationConfig
){

    private val logger = LoggerFactory.getLogger(FlightSimulator::class.java)
    private val flights = mutableListOf<Flight>()
    private val flightFlow = MutableSharedFlow<Flight>(replay = 0, extraBufferCapacity = 100)
    private var lastUpdateTime = Instant.now()
    private var running = false

}