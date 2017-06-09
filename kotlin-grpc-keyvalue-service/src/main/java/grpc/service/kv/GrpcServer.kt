package grpc.service.kv

fun main(args: Array<String>) {

    val server = io.grpc.ServerBuilder.forPort(15000).addService(KeyValueService()).build()
    server.start()

    println("Key value service started")

    Runtime.getRuntime().addShutdownHook(Thread() { println("Ups, JVM shutdown") })
    server.awaitTermination()

    println("Key value service stopped")
}
