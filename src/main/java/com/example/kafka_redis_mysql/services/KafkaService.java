package com.example.kafka_redis_mysql.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class KafkaService {

    @Value("${kafka.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    // A queue to temporarily store consumed messages
    private Queue<String> consumedMessages = new LinkedBlockingQueue<>();

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Producer - Send message to Kafka
    public void sendMessage(String message) {
        kafkaTemplate.send(topicName, message);
        System.out.println("Sent message: " + message + " to topic: " + topicName);
    }

    // Consumer - Listen to messages from Kafka and store them in the queue
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message) {
        consumedMessages.offer(message);
        System.out.println("Consumed message: " + message);
    }

    // Get the most recent consumed message (for real-time retrieval)
    public String getConsumedMessage() {
        return consumedMessages.poll(); // Return and remove the message from the queue
    }
}
