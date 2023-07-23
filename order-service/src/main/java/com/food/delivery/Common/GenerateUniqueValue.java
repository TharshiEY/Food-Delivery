package com.food.delivery.Common;

import com.food.delivery.Entity.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class GenerateUniqueValue {

    @Autowired
    OrderRepository orderRepository;

    public String generateUniqueOrderId() {
        String uniqueOrderId;
        boolean isDuplicate;

        do {
        // Get the current timestamp in milliseconds
        long timestamp = System.currentTimeMillis();

        // Generate a random alphabet
        Random random = new Random();
        String randomLetters = IntStream.range(0, 5)
                .mapToObj(i -> (char) (random.nextInt(26) + 'A'))
                .map(Object::toString)
                .collect(Collectors.joining());

        // Generate a random number between 100 and 999
        int randomNum = ThreadLocalRandom.current().nextInt(100, 1000);

        // Create the unique orderId by combining the timestamp and random number
        uniqueOrderId = "ORD" + timestamp + randomLetters + randomNum;

        // Check the riderId is duplicate
        isDuplicate = orderRepository.existsByOrderId(uniqueOrderId);

        } while (isDuplicate);

        return uniqueOrderId;
    }
}