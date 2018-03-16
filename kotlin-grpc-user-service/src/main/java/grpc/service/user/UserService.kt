package grpc.service.user

import com.google.common.base.Preconditions
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import services.GetRequest
import services.KeyValueServiceGrpcKt.KeyValueServiceKtStub
import services.UserRequest
import services.UserResponse
import services.UserServiceGrpcKt.UserServiceImplBase

class UserService(keyValue: KeyValueServiceKtStub) : UserServiceImplBase() {

    private val keyValue = Preconditions.checkNotNull(keyValue)

    override fun getUser(request: UserRequest): Deferred<UserResponse> = async {
        fun getValue(key: String) = keyValue.get(
                GetRequest
                        .newBuilder()
                        .setKey(request.name + key)
                        .build() ?: throw IllegalArgumentException("key not found")
        )

        val email = getValue(".email")
        val country = getValue(".country")
        val active = getValue(".active")
        UserResponse
                .newBuilder()
                .setName(request.name ?: throw IllegalArgumentException("name can not be null"))
                .setEmailAddress(email.await().value)
                .setCountry(country.await().value)
                .setActive(active.await().value.toBoolean())
                .build()
    }

}
