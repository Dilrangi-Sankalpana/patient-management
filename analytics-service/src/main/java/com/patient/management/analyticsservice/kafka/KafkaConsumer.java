package com.patient.management.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "PATIENT", groupId = "analytics-service")
    public void eventConsumer(byte[] event) {

        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);

            // ... Perform any business logics related to analytics-service

            log.info("Received Patient Event {}", patientEvent);
        } catch (InvalidProtocolBufferException e) {
            log.error(e.getMessage());
        }
    }
}
