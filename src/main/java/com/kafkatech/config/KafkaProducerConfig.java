package com.kafkatech.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;
    /*
    * Configuration For text message producer publish
    */
    @Bean
    public Map<String, Object> textProducerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
    @Bean
    public ProducerFactory<String, Object> textProducerFactory() {
        return new DefaultKafkaProducerFactory<>(textProducerConfig());
    }
    @Bean(name = "TextKafkaTemplate")
    public KafkaTemplate<String, Object> textKafkaTemplate() {
        return new KafkaTemplate<>(textProducerFactory());
    }

    /*
     * Configuration For json message producer publish
     */
    @Bean
    public Map<String, Object> jsonProducerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
    @Bean
    public ProducerFactory<String, Object> jsonProducerFactory() {
        return new DefaultKafkaProducerFactory<>(jsonProducerConfig());
    }
    @Bean(name = "JsonKafkaTemplate")
    public KafkaTemplate<String, Object> jsonKafkaTemplate() {
        return new KafkaTemplate<>(jsonProducerFactory());
    }

}
