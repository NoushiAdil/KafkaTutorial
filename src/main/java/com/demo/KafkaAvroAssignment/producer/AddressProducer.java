package com.demo.KafkaAvroAssignment.producer;

import com.demo.KafkaAvroAssignment.avro.schema.EmployeeAddress;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;
@Service
public class AddressProducer {
    private static final Logger log= LoggerFactory.getLogger(PersonalProducer.class);
    public void avro_serialAddress(EmployeeAddress empAddress){
        String bootstrapServers = "localhost:9092";
        String schemaRegistryUrl = "http://localhost:8081";
        String topic = "Address-Details";
        // Configure the producer
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url", schemaRegistryUrl);

        // Create Kafka producer
        Producer<String, EmployeeAddress> producer = new KafkaProducer<>(props);
        EmployeeAddress employeeAddress = EmployeeAddress.newBuilder().setEmpId(empAddress.getEmpId()).setHouseName(empAddress.getHouseName()).setStreetName(empAddress.getStreetName()).setCity(empAddress.getCity()).setPostcode(empAddress.getPostcode()).setDistrict(empAddress.getDistrict()).setState(empAddress.getState()).setCountry(empAddress.getCountry()).build();

        // Produce Avro message
        ProducerRecord<String, EmployeeAddress> record = new ProducerRecord<>("Address-Details", String.valueOf(employeeAddress.getEmpId()), employeeAddress);
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    System.out.println("Record sent successfully");
                    //System.out.println(metadata.toString());
                    log.info(String.valueOf(record));
                }else {
                    System.out.println("Error while sending record");
                }
            }
        });
        log.info("Finished sending msgs to kafka");
        producer.close();

    }
}
