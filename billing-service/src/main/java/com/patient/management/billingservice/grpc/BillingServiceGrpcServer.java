package com.patient.management.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;

import io.grpc.stub.StreamObserver;

import net.devh.boot.grpc.server.service.GrpcService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class is the server-side implementation of the gRPC contract defined in the billing-service.proto file
// This is place where the actual business logic executes when another service calls gRPC API

@GrpcService
// Register this class as a gRPC server endpoint
// Spring Boot will detect this, start a gRPC server, and Expose this service
public class BillingServiceGrpcServer extends BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcServer.class);

    // StreamObserver<BillingResponse> responseObserver is used to send response back to client
    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {

        log.info("createBillingAccount request received : {}", billingRequest.toString());

        // Business logic - e.g. save to database, perform calculations etc

        BillingResponse billingResponse = BillingResponse.newBuilder().setAccountId("12345").setStatus("Active").build();

        // Sends response to client
        responseObserver.onNext(billingResponse);

        // Response is finished
        responseObserver.onCompleted();
    }

}
