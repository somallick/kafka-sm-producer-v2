package com.kafkatech.controller;

import com.kafkatech.dto.CustomerDTO;
import com.kafkatech.service.KafkaJsonMessagePublisher;
import com.kafkatech.service.KafkaTextMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publish")
public class KafkaEventController {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventController.class);
    @Autowired
    private KafkaTextMessagePublisher kafkaTextMessagePublisher;
    @Autowired
    private KafkaJsonMessagePublisher kafkaJsonMessagePublisher;

    @PostMapping("/text")
    public ResponseEntity<?> publishMessage(@RequestBody String message) {
        try {
            kafkaTextMessagePublisher.sendTextMessageToTopic(message);
            return ResponseEntity.ok("Text message published successfully ..");
        } catch (Exception ex) {
            log.error("Error occurred while publishing text message - {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/json")
    public ResponseEntity<?> publishJsonMessage(@RequestBody CustomerDTO customerDTO) {
        try{
            kafkaJsonMessagePublisher.sendJsonMessageToTopic(customerDTO);
            return ResponseEntity.ok("Json message published successfully ..");
        } catch (Exception ex) {
            log.error("Error occurred while publishing json message - {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
