package com.food.delivery.Common;

import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class GenerateUniqueValue {
    public String generateUniqueOrderId() {
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
        String uniqueOrderId = "ORD" + timestamp + randomLetters + randomNum;

        return uniqueOrderId;
    }
}