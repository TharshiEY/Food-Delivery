package com.food.restaurantservice.Common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    KafkaConfig kafkaConfig;
    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName,String message) {
        ListenableFuture<SendResult<Integer, String>> future = kafkaConfig.kafkaTemplate().send(topicName,message);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message = {} dut to: {}", message.toString(), ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("Message sent successfully with offset = {}", result.getRecordMetadata().offset());
            }
        });
        System.out.println("Message sent: " + message);
    }

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void sendWithRetry(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}

