package grpc.service.user

import com.google.common.base.Preconditions
import io.grpc.stub.StreamObserver
import services.GetRequest
import services.KeyValueServiceGrpc.KeyValueServiceBlockingStub
import services.UserRequest
import services.UserResponse
import services.UserServiceGrpc.UserServiceImplBase

class UserService(keyValue: KeyValueServiceBlockingStub) : UserServiceImplBase() {

    private val keyValue = Preconditions.checkNotNull(keyValue)

    override fun getUser(request: UserRequest, responseObserver: StreamObserver<UserResponse>) {
        val response = UserResponse.newBuilder()

        response.name = request.name

        response.emailAddress = keyValue.get(GetRequest.newBuilder()
                .setKey(request.name + ".emailAddress")
                .build())
                .value

        response.country = keyValue.get(GetRequest.newBuilder()
                .setKey(request.name + ".country")
                .build())
                .value

        response.active = java.lang.Boolean.valueOf(keyValue.get(GetRequest.newBuilder()
                .setKey(request.name + ".active")
                .build())
                .value)!!

        responseObserver.onNext(response.build())
        responseObserver.onCompleted()
    }

}
