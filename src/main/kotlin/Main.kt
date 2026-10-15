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
        }
        catch(Exception e) {
            val configFil=args.getOrNull(0)
        }
    }
}


