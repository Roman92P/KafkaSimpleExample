package group;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaGroupProducerApp {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092,localhost:9093");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> myProducer = new KafkaProducer<>(properties);

        try {
            int counter = 0;
            while (counter < 100) {
                myProducer.send(new ProducerRecord<String, String>("my-topic","qwerty"));
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myProducer.close();
        }
    }

}
