package com.kafkatech.config;

import com.kafkatech.dto.TopicDetail;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Data
@ConfigurationProperties(prefix = "spring.kafka")
public class TopicListConfig {
    private List<TopicDetail> topics;
}
