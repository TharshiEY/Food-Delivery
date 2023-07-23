package com.food.delivery.Common;

public class AppConfigs {
    final static String applicationID = "HelloProducer";
    final static String bootstrapServers= "localhost:9092,localhost:9093";
    final static String topicName = "order-topic";
    final static int numEvents = 5;
    final static String groupID = "console-consumer-39158";
}
