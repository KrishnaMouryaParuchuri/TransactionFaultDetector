package com.example.consumer;

import com.example.model.Fault;
import com.example.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
public class TransactionConsumer {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    private static final int MAX_TRANSACTIONS = 5;
    private static final long WINDOW_SECONDS = 60;

    @KafkaListener(topics = "transactions", groupId = "transaction-consumer")
    public void consume(Transaction transaction) {
        String userId = transaction.getUserId();
        String redisKey = "user:transaction:count:" + userId;

        // Increment transaction count in Redis
        Long count = redisTemplate.opsForValue().increment(redisKey);
        if (count == 1) {
            // Set expiry for the key (1 minute)
            redisTemplate.expire(redisKey, WINDOW_SECONDS, TimeUnit.SECONDS);
        }

        // Check for fault
        if (count > MAX_TRANSACTIONS) {
            Fault fault = new Fault();
            fault.setUserId(userId);
            fault.setTransactionCount(count.intValue());
            fault.setDetectedAt(Instant.now());

            // Save fault to Elasticsearch
            elasticsearchOperations.save(fault, IndexCoordinates.of("faults"));

            System.out.println("Fault detected for user " + userId + ": " + count + " transactions in 1 minute");
        }
    }
}