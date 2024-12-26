package com.kafkatech;

import com.kafkatech.config.TopicListConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicListConfig.class)
public class KafkaSmProducerV2Application {

	public static void main(String[] args) {
		SpringApplication.run(KafkaSmProducerV2Application.class, args);
	}

}
