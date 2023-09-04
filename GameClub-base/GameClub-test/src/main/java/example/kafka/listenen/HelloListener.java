package example.kafka.listenen;

import com.alibaba.fastjson.JSON;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class HelloListener {
    @KafkaListener(topics = "test-topic")
    public void onMessage(String message){
        if(!StringUtils.isEmpty(message)){
            System.out.println(message+ new Date());
        }
    }
    @KafkaListener(topics = "testEntity-topic")
    public void onMessage2(String message){
        if(!StringUtils.isEmpty(message)){
            System.out.println(JSON.parseObject(message));
        }
    }
}
