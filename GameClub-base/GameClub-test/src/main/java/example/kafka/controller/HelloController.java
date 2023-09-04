package example.kafka.controller;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    @GetMapping("/hello")
    public String hello(){
        kafkaTemplate.send("test-topic","测试数据");
        return "ok";
    }
    @GetMapping("/helloEntity")
    public String helloEntity(){
        User user = new User();
        user.setName("testliu");
        user.setAge(99);
        kafkaTemplate.send("testEntity-topic", JSON.toJSONString(user));
        return "ok";
    }

}
@Data
class User{
    private String name;
    private int age;
}
