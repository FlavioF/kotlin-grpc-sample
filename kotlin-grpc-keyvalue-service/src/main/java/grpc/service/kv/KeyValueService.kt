package grpc.service.kv

import com.google.common.collect.ImmutableMap
import com.google.common.collect.Maps
import io.grpc.stub.StreamObserver
import services.GetRequest
import services.GetResponse
import services.KeyValueServiceGrpc.KeyValueServiceImplBase
import services.PutRequest
import services.PutResponse
import java.util.*

/**
 * Created by fferreira on 03-06-2017.
 */
class KeyValueService : KeyValueServiceImplBase() {

    internal var store: MutableMap<String, String> = Maps.newHashMap(ImmutableMap.builder<String, String>()
            .put("flavio.emailAddress", "flavio@gmail.com")
            .put("flavio.country", "PT")
            .put("flavio.active", "true")
            .put("carol.emailAddress", "carol@gmail.com")
            .put("carol.country", "UK")
            .put("carol.active", "true")
            .put("ines.emailAddress", "ines@gmail.com")
            .put("ines.country", "Spain")
            .put("ines.active", "false")
            .build()
    )

    override fun put(request: PutRequest, responseObserver: StreamObserver<PutResponse>) {
        store.put(request.key, request.value)
        responseObserver.onNext(PutResponse.newBuilder().build())
        responseObserver.onCompleted()
    }

    override fun get(request: GetRequest, responseObserver: StreamObserver<GetResponse>) {
        val response = GetResponse.newBuilder()

        val value = store[request.key]

        if (value != null) {
            response.value = value
        }

        responseObserver.onNext(response.build())
        responseObserver.onCompleted()
    }


}