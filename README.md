# Simple Sample Kotlin gRPC service

Very simple Java8 sample of a gRPC Kotlin service.
Check Java9 branch to check Java9 sample.

This samples is composed by two gRPC services (user and key value) and one client.
The user service must get data from the key value service to provide all information about the user to a client.



### Pre requirements
* Java 8
* Maven

### About gRPC
...

### About Kotlin
...

## Try it


**Build**
```
mvn clean install
```
### Run key value service
```
mvn -pl kotlin-grpc-keyvalue-service exec:java
```
### Run user service
```
mvn -pl kotlin-grpc-user-service exec:java
```
### Run the client
```
mvn -pl kotlin-grpc-client exec:java
```
