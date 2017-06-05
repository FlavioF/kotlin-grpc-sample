package grpc.service.kv


fun main(args: Array<String>) {

    val server = io.grpc.ServerBuilder.forPort(15000).addService(KeyValueService()).build()
    server.start()

    println("Server started")

    Runtime.getRuntime().addShutdownHook(Thread() { println("Ups, JVM shutdown") })
    server.awaitTermination()

    println("Server stopped")
}
