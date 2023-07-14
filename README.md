# Food-Delivery
 Food delivery service 
 With Kafka and Rabbit MQ


## KAFKA
###Cmd
###start the zookeeper
bin\windows\zookeeper-server-start.bat etc\kafka\zookeeper.properties

###Start kafka server
bin\windows\kafka-server-start.bat etc\kafka\server.properties


Zookeeper is kind of storage of kafka

##Apache Kafka free version 

###*start the zookeeper
 bin\windows\zookeeper-server-start.bat config\zookeeper.properties

	Start the Kafka server:-  bin\windows\kafka-server-start.bat config\server.properties

	Create the topic:-  
    bin\windows\kafka-topics.bat --create --topic test --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092

	Ø Send the data :- 
    bin\windows\kafka-console-producer.bat --topic test --broker-list localhost:9092 < ..\data\sample1.csv

	Ø Read the data from kafka cluster from beginning :- 
    bin\windows\kafka-console-consumer.bat --topic test --bootstrap-server localhost:9092 --from-beginning

If we are running multiple broker in one machine we have to change the listeners port 

Clean the data
In the folder window path you have to write 
\tmp

	Ø Create a consumer group
	bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic stock-ticks --from-beginning --group group1

	Ø Dump the log file
    bin\windows\kafka-dump-log.bat --files c:\tmp\kafka-logs-1\stock-ticks-1\00000000000000000000.log 

	Ø Setup the configuration for the log dir creation 
need to change zookeeper.properties file -> dataDir=../tmp/zookeeper
	
	Ø Set up environment variable 
    setx KAFKA_HOME C:\kafka\kafka_2.13-3.4.1 
![image](https://github.com/TharshiEY/Food-Delivery/assets/133849244/c645307c-e62e-4f43-8cd6-017746261dc4)

## RabbitMQ
Start the RabbitMQ server setup
Cd :- C:\RabbitMq\rabbitmq-server-windows-3.12.0\rabbitmq_server-3.12.0\sbin
Cmd :- rabbitmq-plugins.bat enable rabbitmq-management



If above scree shot error occur you have to execute the command
CMD:- rabbitmq-plugins.bat enable rabbitmq_management

Started the server



Consumer Jar file start command 



 remove queues message command 
"Queue-1" => is queue name





![image](https://github.com/TharshiEY/Food-Delivery/assets/133849244/01dca34d-87aa-4311-a3dd-07e9d4254e62)
