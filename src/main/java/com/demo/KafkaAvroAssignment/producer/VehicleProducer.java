package com.demo.KafkaAvroAssignment.producer;

import com.demo.KafkaAvroAssignment.avro.schema.EmployeeVehicle;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;
@Service
public class VehicleProducer {
    private static final Logger log= LoggerFactory.getLogger(PersonalProducer.class);
    public void avro_serialVehicle(EmployeeVehicle empVehicle){
        String bootstrapServers = "localhost:9092";
        String schemaRegistryUrl = "http://localhost:8081";
        String topic = "Vehicle-Details";
        // Configure the producer
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url", schemaRegistryUrl);

        // Create Kafka producer
        Producer<String, EmployeeVehicle> producer = new KafkaProducer<>(props);
        EmployeeVehicle employeeVehicle = EmployeeVehicle.newBuilder().setEmpId(empVehicle.getEmpId()).setVehicleNumber(empVehicle.getVehicleNumber()).setVehicleName(empVehicle.getVehicleName()).setInsuranceDetails(empVehicle.getInsuranceDetails()).setManufacturingYear(empVehicle.getManufacturingYear()).build();

        // Produce Avro message
        ProducerRecord<String, EmployeeVehicle> record = new ProducerRecord<>("Vehicle-Details", String.valueOf(employeeVehicle.getEmpId()), employeeVehicle);
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
