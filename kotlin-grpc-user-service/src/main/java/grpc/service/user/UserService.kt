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

        fun getValue(key: String) = keyValue.get(
                GetRequest
                        .newBuilder()
                        .setKey(request.name + key)
                        .build() ?: throw IllegalArgumentException("key not found")
        ).value

        val response = UserResponse
                .newBuilder()
                .setName(request?.name ?: throw IllegalArgumentException("name can not be null"))
                .setEmailAddress(getValue(".email"))
                .setCountry(getValue(".country"))
                .setActive(getValue(".active").toBoolean())
                .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

}
