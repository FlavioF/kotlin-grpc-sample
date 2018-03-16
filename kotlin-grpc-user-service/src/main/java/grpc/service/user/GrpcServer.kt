package grpc.service.user

import io.grpc.ManagedChannelBuilder
import io.grpc.ServerBuilder
import services.KeyValueServiceGrpcKt

fun main(args: Array<String>) {

    val channel = ManagedChannelBuilder.forAddress("localhost", 15000).usePlaintext(true).build()
    val kvsClient = KeyValueServiceGrpcKt.newStub(channel)

    val server = ServerBuilder.forPort(15001).addService(UserService(kvsClient)).build()
    server.start()

    println("User service started")

    Runtime.getRuntime().addShutdownHook(Thread() { println("Ups, JVM shutdown") })
    server.awaitTermination()

    println("User service stopped")
}
