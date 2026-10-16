package org.example



object Main{

    private  val logger = LoggerFactory.getLogger(Main::class.java)
    private lateint var simulator: FlightSimulator
    private lateint var producer: FlightProducer

    @JvmStatic
    fun main(args: Array<String>) = runBlocking(Dispatchers.Default) {
        logger.info("Starting Flight Data Generator")
        try {
            val configFil=args.getOrNull(0)
            
            simulator = FlightSimulator(config.simulation)
            simulator.initialize()
            producer = FlightProducer(config.kafka)


            simulator.start(this)
            producer.start(simulator.getFlightFlow(), this)
            logger.info("data generator started")

        }
        catch(Exception e) {
            val configFil=args.getOrNull(0)
        }
    }
}


