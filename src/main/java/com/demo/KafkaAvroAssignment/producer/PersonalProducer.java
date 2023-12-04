package com.demo.KafkaAvroAssignment.producer;
import com.demo.KafkaAvroAssignment.avro.schema.EmployeePersonal;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonalProducer {
    private static final Logger log= LoggerFactory.getLogger(PersonalProducer.class);
    public void avro_serialPersonal(EmployeePersonal empPersonal){
        String bootstrapServers = "localhost:9092";
        String schemaRegistryUrl = "http://localhost:8081";
        String topic = "Personal-Details";
        // Configure the producer
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url", schemaRegistryUrl);

        // Create Kafka producer
        Producer<String, EmployeePersonal> producer = new KafkaProducer<>(props);
        EmployeePersonal employeePersonal = EmployeePersonal.newBuilder().setEmpId(empPersonal.getEmpId()).setFirstName(empPersonal.getFirstName()).setLastName(empPersonal.getLastName()).setAge(empPersonal.getAge()).setSex(empPersonal.getSex()).build();

        // Produce Avro message
        ProducerRecord<String, EmployeePersonal> record = new ProducerRecord<>("Personal-Details", String.valueOf(employeePersonal.getEmpId()), employeePersonal);
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
