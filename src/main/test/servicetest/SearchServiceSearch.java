package servicetest;

import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.service.SearchService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SearchServiceSearch {



    @Test
    public void SearchTest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        SearchService searchService = applicationContext.getBean(SearchService.class);


        List<Document> documentList = searchService.keywordSearch("hello");

        System.out.println(documentList);
    }
}
