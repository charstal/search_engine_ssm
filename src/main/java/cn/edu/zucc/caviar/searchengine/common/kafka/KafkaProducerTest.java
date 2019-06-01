package cn.edu.zucc.caviar.searchengine.common.kafka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;



public class KafkaProducerTest {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        KafkaProducerServer kafkaProducer = applicationContext.getBean(KafkaProducerServer.class);
        String topic = "orderTopic";
        String value = "test";
        String ifPartition = "1";
        Integer partitionNum = 3;
        String role = "test";//用来生成key
        Map<String,Object> res = kafkaProducer.sndMesForTemplate(topic, value, ifPartition, partitionNum, role);

        System.out.println("测试结果如下：===============");
        String message = (String)res.get("message");
        String code = (String)res.get("code");

        System.out.println("code:"+code);
        System.out.println("message:"+message);
    }
}