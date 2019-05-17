package cn.edu.zucc.caviar.searchengine.core.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

//@Controller
public class KafkaController {

    @Resource
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @RequestMapping(value = "/hello.do")
    public void hello(){

        kafkaTemplate.sendDefault("test it");

    }
}
