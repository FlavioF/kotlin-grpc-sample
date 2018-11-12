package grpc.client

import com.google.common.collect.Iterators
import io.grpc.ManagedChannelBuilder
import services.UserRequest
import services.UserServiceGrpc
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {

    val channel = ManagedChannelBuilder.forAddress("localhost", 15001).usePlaintext().build()
    val blockingStub = UserServiceGrpc.newBlockingStub(channel)

    try {
        val users = Iterators.cycle("radha", "kanti", "chanda", "kali")
        var i = 0
        while (users.hasNext() && i < 100) {

            val request = UserRequest.newBuilder().setName(users.next()).build()

            println("Response from server:\n${blockingStub.getUser(request)}")
            i++
        }

    } finally {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}
