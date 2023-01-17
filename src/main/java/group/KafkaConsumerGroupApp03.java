package group;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KafkaConsumerGroupApp03 {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092,localhost:9093");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "default");
//        properties.put("enable.auto.commit", "false");
        //properties.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        List<String> topics = new ArrayList<String>();
        topics.add("my-topic");

        kafkaConsumer.subscribe(topics);
        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Topic: %s, Partition %d, Value: %s",
                            record.topic(), record.partition(),record.value().toUpperCase());
                    System.out.println();
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        finally {
            kafkaConsumer.close();
            System.out.println("The end");
        }
    }
}
