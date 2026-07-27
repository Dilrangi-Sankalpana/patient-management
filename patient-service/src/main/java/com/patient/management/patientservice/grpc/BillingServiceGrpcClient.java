package com.patient.management.patientservice.grpc;

// Generated classes from .proto file
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;

import com.patient.management.patientservice.entity.Patient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    // Remote method caller
    private final BillingServiceGrpc.BillingServiceBlockingStub billingServiceBlockingStub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}")  String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ) {
        log.info("{}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        billingServiceBlockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public void createBillingRequest(Patient patient) {

        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .build();

        BillingResponse response = billingServiceBlockingStub.createBillingAccount(request);

        log.info("Received response from billing service via GRPC:{}", response);
    }
}
