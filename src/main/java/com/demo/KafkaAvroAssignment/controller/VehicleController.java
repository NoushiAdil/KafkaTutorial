package com.demo.KafkaAvroAssignment.controller;

import com.demo.KafkaAvroAssignment.avro.schema.EmployeeAddress;
import com.demo.KafkaAvroAssignment.avro.schema.EmployeePersonal;
import com.demo.KafkaAvroAssignment.avro.schema.EmployeeVehicle;
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
public class VehicleController {
    @Autowired
    private VehicleProducer vehicleProducer;
    @Autowired
    public VehicleController(PersonalProducer personalProducer) {
        this.vehicleProducer = vehicleProducer;
    }
    @PostMapping(value="/sendVehicleDetails")
    public ResponseEntity<String> sendVehicleDetails(@RequestBody EmployeeVehicle employeeVehicle) {
        vehicleProducer.avro_serialVehicle(employeeVehicle);
        return  ResponseEntity.ok("Message send to topic");

    }
}
