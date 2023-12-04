package com.demo.KafkaAvroAssignment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class PersonalTopicConfiguration {

        @Bean
        public NewTopic EmployeePersonalTopicConfig(){

            return TopicBuilder.name("Personal-Details")
                    .partitions(5)
                    .replicas(1)
                    .build();
        }
        @Bean
        public NewTopic EmployeeVehicleTopicConfig(){

            return TopicBuilder.name("Vehicle-Details")
                .partitions(5)
                .replicas(1)
                .build();
        }
        public NewTopic EmployeeAddressTopicConfig(){

            return TopicBuilder.name("Address-Details")
                .partitions(5)
                .replicas(1)
                .build();
    }

}
