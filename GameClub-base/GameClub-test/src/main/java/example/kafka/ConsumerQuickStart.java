package example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerQuickStart {
    public static void main(String[] args) {
        //1.kafka配置信息
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"47.113.144.107:9092");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"group1");
        //2.创建消费者对象
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String,String>(prop);
        //3.订阅主题
        consumer.subscribe(Collections.singletonList("topic-first"));
        //4.拉取消息
        while (true){
            ConsumerRecords<String,String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String,String> consumerRecord:consumerRecords) {
                System.out.println(consumerRecord.key());
                System.out.println(consumerRecord.value());
            }
        }
    }

}
