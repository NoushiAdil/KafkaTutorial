package com.demo.KafkaAvroAssignment.controller;

import com.demo.KafkaAvroAssignment.avro.schema.EmployeePersonal;
import com.demo.KafkaAvroAssignment.producer.PersonalProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("kafka/v1/")
@RestController
public class PersonalController {
    @Autowired
    private PersonalProducer personalProducer;
    @Autowired
    public PersonalController(PersonalProducer personalProducer) {
        this.personalProducer = personalProducer;
    }
    @PostMapping(value="/sendPersonalDetails")
    public ResponseEntity<String> sendPersonalDetails(@RequestBody EmployeePersonal employeePersonal) {
        personalProducer.avro_serialPersonal(employeePersonal);
        return  ResponseEntity.ok("Message send to topic");

    }
}
