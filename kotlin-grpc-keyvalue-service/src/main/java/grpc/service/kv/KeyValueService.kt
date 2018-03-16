package grpc.service.kv

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import services.GetRequest
import services.GetResponse
import services.KeyValueServiceGrpcKt.KeyValueServiceImplBase
import services.PutRequest
import services.PutResponse

class KeyValueService : KeyValueServiceImplBase() {

    val names = listOf("radha", "kanti", "chanda", "kali")

    internal var store: MutableMap<String, String> = hashMapOf(
            "${names[0]}.email" to "${names[0]}@gmail.com",
            "${names[0]}.country" to "Portugal",
            "${names[0]}.active" to "true",
            "${names[1]}.email" to "${names[1]}@gmail.com",
            "${names[1]}.country" to "France",
            "${names[1]}.active" to "true",
            "${names[2]}.email" to "${names[2]}@gmail.com",
            "${names[2]}.country" to "Spain",
            "${names[2]}.active" to "true",
            "${names[3]}.email" to "${names[3]}@gmail.com",
            "${names[3]}.country" to "UK",
            "${names[3]}.active" to "false"
    )

    override fun put(request: PutRequest): Deferred<PutResponse> = async {
        store.put(request.key ?: throw IllegalArgumentException("key can not be null"),
                request.value ?: throw IllegalArgumentException("value can not be null")
        )
        PutResponse.getDefaultInstance()
    }

    override fun get(request: GetRequest): Deferred<GetResponse> = async {
        GetResponse
                .newBuilder()
                .setValue(
                        store[request.key ?: throw IllegalArgumentException("key can not be null")]
                                ?: throw IllegalArgumentException("${request.key} key not found")
                ).build()
    }

}
