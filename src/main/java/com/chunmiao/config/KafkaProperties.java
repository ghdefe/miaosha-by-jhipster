package com.chunmiao.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private String bootStrapServers = "localhost:9092";

    private Map<String, String> consumer = new HashMap<>();

    private Map<String, String> producer = new HashMap<>();

    public String getBootStrapServers() {
        return bootStrapServers;
    }

    public void setBootStrapServers(String bootStrapServers) {
        this.bootStrapServers = bootStrapServers;
    }

    public Map<String, Object> getConsumerProps() {
        Map<String, Object> properties = new HashMap<>(this.consumer);
        if (!properties.containsKey("bootstrap.servers")) {
            properties.put("bootstrap.servers", this.bootStrapServers);
        }
        return properties;
    }

    public void setConsumer(Map<String, String> consumer) {
        this.consumer = consumer;
    }

    public Map<String, Object> getProducerProps() {
        Map<String, Object> properties = new HashMap<>(this.producer);
        if (!properties.containsKey("bootstrap.servers")) {
            properties.put("bootstrap.servers", this.bootStrapServers);
        }
        return properties;
    }

    public void setProducer(Map<String, String> producer) {
        this.producer = producer;
    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> getConsumer() {
//        ConcurrentKafkaListenerContainerFactory<String, String> kafkaFactory = new ConcurrentKafkaListenerContainerFactory<>();
//        kafkaFactory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getConsumerProps()));
//        return kafkaFactory;
//    }


}
