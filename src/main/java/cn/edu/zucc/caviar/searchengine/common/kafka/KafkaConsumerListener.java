package cn.edu.zucc.caviar.searchengine.common.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

public class KafkaConsumerListener implements MessageListener<Integer, String> {



    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {

        Object o = consumerRecord.value();
        System.out.println(String.valueOf(o));
    }
}
