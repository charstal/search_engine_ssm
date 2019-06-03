package cn.edu.zucc.caviar.searchengine.common.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * rabbitMQ消费者
 * */



public class MessageConsumer implements MessageListener {

private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);


@Override
public void onMessage(Message message) {
        logger.info("receive message:{}",message);
        }

public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        MessageConsumer messageConsumer = applicationContext.getBean(MessageConsumer.class);

        }

        }
