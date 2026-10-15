package org.example



object Main{

    private  val logger = LoggerFactory.getLogger(Main::class.java)
    private lateint var simulator: FlightSimulator
    private lateint var producer: FlightProducer

    @JvmStatic
    fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}
}


