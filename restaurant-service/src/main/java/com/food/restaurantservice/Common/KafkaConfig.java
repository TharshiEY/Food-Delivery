package com.food.restaurantservice.Common;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {


//    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate() {
        Map<String, Object> producerConfigs = new HashMap<>();
        producerConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
        producerConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        producerConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs));
    }
}
