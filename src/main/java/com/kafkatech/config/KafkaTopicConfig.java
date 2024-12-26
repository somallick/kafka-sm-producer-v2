package com.kafkatech.config;

import com.kafkatech.dto.TopicDetail;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaTopicConfig.class);
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;
    @Autowired
    private TopicListConfig topicListConfig;
    @Bean
    public KafkaAdmin kafkaAdmin() {
        KafkaAdmin kafkaAdmin = new KafkaAdmin(Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers));
        return kafkaAdmin;
    }
    @Bean
    public List<NewTopic> createTopics() {
        List<NewTopic> newTopics = new ArrayList<>();
        if(topicListConfig != null && !topicListConfig.getTopics().isEmpty()) {
            for (TopicDetail topic : topicListConfig.getTopics()) {
                newTopics.add(new NewTopic(topic.getName(), topic.getPartitions(), (short) topic.getReplicationFactor()));
            }
        } else {
            log.error("Kafka Topic List is either null or empty");
        }
        return newTopics;
    }
}
