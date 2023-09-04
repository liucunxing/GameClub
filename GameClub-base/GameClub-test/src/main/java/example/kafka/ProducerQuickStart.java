package example.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerQuickStart {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.kafka连接配置
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"47.113.144.107:9092");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        //2.创建kafka生产者对象
        KafkaProducer<String,String> producer = new KafkaProducer<String,String>(prop);

        //3.发送消息
        ProducerRecord<String,String> kvProducerRecord = new ProducerRecord<String,String>("topic-first","key-001","hello");
        //同步发送消息
//        RecordMetadata recordMetadata = producer.send(kvProducerRecord).get();
//        System.out.println(recordMetadata.offset());
        //异步发送消息
        producer.send(kvProducerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e != null){
                    System.out.println("记录异常到日志");
                }
                System.out.println(recordMetadata.offset());
            }
        });

        //4.关闭消息通道
        producer.close();
    }
}
