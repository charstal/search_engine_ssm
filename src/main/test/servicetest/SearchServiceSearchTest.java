package servicetest;

import cn.edu.zucc.caviar.searchengine.common.utils.RedisTest;
import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.service.SearchService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.print.Doc;
import java.util.List;
import java.util.Set;

public class SearchServiceSearchTest {



    @Test
    public void SearchTest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        SearchService searchService = applicationContext.getBean(SearchService.class);

        System.out.println(searchService.keywordSearch("meishi", 2, ""));
        Set<Document> documents = searchService.documentsInPage(1,10);

        for(Document doc : documents){
            System.out.println(doc.getPublishDate());
        }
    }

    @Test
    public void RedisTest(){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        RedisTest redisTest = applicationContext.getBean(RedisTest.class);
//        redisTest.zunion("test","杭州","杭州");
        Set<Object> sets = redisTest.resultPagingWithScores(1,10);
        for(Object doc:sets){
            System.out.println((String)doc);
        }
    }

}
