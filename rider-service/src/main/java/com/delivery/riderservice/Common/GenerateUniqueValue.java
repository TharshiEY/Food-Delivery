package com.delivery.riderservice.Common;

import com.delivery.riderservice.Dto.RiderDto;
import com.delivery.riderservice.Entity.Rider;
import com.delivery.riderservice.Entity.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class GenerateUniqueValue {
    @Autowired
    RiderRepository riderRepository;

    public String generateUniqueOrderId() {

        // Generate a new uniqueOrderId and initialize a flag to check for duplicates
        String uniqueRiderId;
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
        int randomNum1 = ThreadLocalRandom.current().nextInt(100, 999);
        int randomNum2 = ThreadLocalRandom.current().nextInt(10, 99);

        // Create the unique riderId by combining the randomLetters and random number
        uniqueRiderId = "RID" + randomNum1 + randomLetters + randomNum2;

        // Check the riderId is duplicate
        isDuplicate = riderRepository.existsByOrderId(uniqueRiderId);

        } while (isDuplicate);

        return uniqueRiderId;

    }

}