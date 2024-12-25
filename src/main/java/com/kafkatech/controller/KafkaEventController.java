package com.kafkatech.controller;

import com.kafkatech.dto.CustomerDTO;
import com.kafkatech.service.KafkaJsonMessagePublisher;
import com.kafkatech.service.KafkaMessagePublisher;
import com.kafkatech.service.KafkaMessagePublisher2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer-app")
public class KafkaEventController {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventController.class);
    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;
    @Autowired
    private KafkaMessagePublisher2 kafkaMessagePublisher2;
    @Autowired
    private KafkaJsonMessagePublisher kafkaJsonMessagePublisher;

    @PostMapping("/text/publish/{id}")
    public ResponseEntity<?> publishMessage(@RequestBody String message, @PathVariable String id) {
        try {
            if(Integer.parseInt(id.trim())==1)
                kafkaMessagePublisher.sendMessageToTopic(message);
            else
                kafkaMessagePublisher2.sendMessageProcessWithCallbacks(message);
            return ResponseEntity.ok("message published successfully ..");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/json/publish")
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
