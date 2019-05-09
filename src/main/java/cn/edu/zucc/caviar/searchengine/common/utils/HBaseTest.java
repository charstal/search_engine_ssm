package cn.edu.zucc.caviar.searchengine.common.utils;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;


import java.util.List;


public class HBaseTest {

    @Autowired
    private HbaseTemplate template;

    @Test
    public void testFind() {
        List<String> rows = template.find("user", "cf", "name", new RowMapper<String>() {
            @Override
            public String mapRow(Result result, int i) throws Exception {
                return result.toString();
            }

        });
    }

    @Test
    public void testPut() {
        template.put("user", "1", "cf", "name", Bytes.toBytes("Alice"));
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        HBaseTest hBaseTest = applicationContext.getBean(HBaseTest.class);

        hBaseTest.testPut();
        hBaseTest.testFind();
    }
}