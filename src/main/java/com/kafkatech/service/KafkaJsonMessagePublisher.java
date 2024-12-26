package com.kafkatech.service;

import com.kafkatech.dto.CustomerDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaJsonMessagePublisher {
    private static final Logger log = LoggerFactory.getLogger(KafkaJsonMessagePublisher.class);
    @Autowired
    @Qualifier("JsonKafkaTemplate")
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private List<NewTopic> kafkaTopicList;

    public void sendJsonMessageToTopic(CustomerDTO customerDTO) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(kafkaTopicList.get(1).name(), customerDTO);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[" + customerDTO +
                        "] with offset=[" + result.getRecordMetadata().offset() +
                        "] in topic=[" + result.getRecordMetadata().topic() +
                        "] in partition=[" + result.getRecordMetadata().partition() +
                        "] having timestamp=[" + result.getRecordMetadata().timestamp() + "]");
            } else {
                log.error("Unable to send message=[" + customerDTO + "] due to : " + ex.getMessage());
            }
        });
    }

}
