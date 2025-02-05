package com.example.kafka_redis_mysql.controllers;

import com.example.kafka_redis_mysql.services.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    // Producer: Endpoint to send a message to Kafka
    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody String message) {
        kafkaService.sendMessage(message);
        
        // Prepare the response as a JSON object
        Map<String, String> response = new HashMap<>();
        response.put("message", "Message sent to Kafka: " + message);
        
        return ResponseEntity.ok(response);
    }

    // Consumer: Endpoint to get the most recent consumed message in real-time
    @GetMapping("/consume")
    public ResponseEntity<Map<String, String>> getConsumedMessage() {
        String message = kafkaService.getConsumedMessage();

        // Prepare the response as a JSON object
        Map<String, String> response = new HashMap<>();
        if (message != null) {
            response.put("message", "Consumed message: " + message);
        } else {
            response.put("message", "No message consumed yet.");
        }
        
        return ResponseEntity.ok(response);
    }
}