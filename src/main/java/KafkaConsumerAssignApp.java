import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.Properties;
import java.lang.System;

public class KafkaConsumerAssignApp {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092,localhost:9093");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

         KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);

         ArrayList<TopicPartition> partitions = new ArrayList<>();

         TopicPartition topicPartition0 = new TopicPartition("my-topic", 0);
         TopicPartition topicPartition2 = new TopicPartition("my-other-topic", 2);

         partitions.add(topicPartition0);
         partitions.add(topicPartition2);

         kafkaConsumer.assign(partitions);

        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(10);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Topic: %s, Partition %d, Offset: %d, Key: %s, Value: %s",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());
                    System.out.println();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            kafkaConsumer.close();
        }

    }

}
