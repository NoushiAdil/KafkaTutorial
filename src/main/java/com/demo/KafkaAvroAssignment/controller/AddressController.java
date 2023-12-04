package com.demo.KafkaAvroAssignment.controller;

import com.demo.KafkaAvroAssignment.avro.schema.EmployeeAddress;
import com.demo.KafkaAvroAssignment.avro.schema.EmployeeVehicle;
import com.demo.KafkaAvroAssignment.producer.AddressProducer;
import com.demo.KafkaAvroAssignment.producer.PersonalProducer;
import com.demo.KafkaAvroAssignment.producer.VehicleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("kafka/v1/")
@RestController
public class AddressController {
    @Autowired
    private AddressProducer addressProducer;
    @Autowired
    public AddressController(AddressProducer addressProducer) {
        this.addressProducer = addressProducer;
    }
    @PostMapping(value="/sendAddressDetails")
    public ResponseEntity<String> sendAddressDetails(@RequestBody EmployeeAddress employeeAddress) {
        addressProducer.avro_serialAddress(employeeAddress);
        return  ResponseEntity.ok("Message send to topic");

    }
}
