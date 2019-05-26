package cn.edu.zucc.caviar.searchengine.common.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * rabbitMQ生产者
 * */
@Service
public class MessageSender {

    private AmqpTemplate amqpTemplate;
    private String routingKey;

    public AmqpTemplate getAmqpTemplate() {
        return amqpTemplate;
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public void sendDataToQueue(Object obj) {
        System.out.println("routingKey = " + this.routingKey);
        System.out.println("Obj = " + obj);
        amqpTemplate.convertAndSend(this.routingKey, obj);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        MessageConsumer messageConsumer = applicationContext.getBean(MessageConsumer.class);

        MessageSender messageSender = applicationContext.getBean(MessageSender.class);
        messageSender.setRoutingKey("Hello");
        messageSender.sendDataToQueue("World");

    }
}