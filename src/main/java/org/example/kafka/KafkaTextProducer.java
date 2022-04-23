package org.example.kafka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaTextProducer {

    public static void main(String[] args) throws Exception{
        String topicName = "wordcount1";
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/resources/KafkaArticle1.txt"));
            String nextLine = "";
            while ((nextLine = br.readLine()) != null) {
                producer.send(new ProducerRecord<String, String>(topicName, nextLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        producer.flush();
        producer.close();
    }
}
