package cn.edu.zucc.caviar.searchengine.common.query.synonym;

import cn.edu.zucc.caviar.searchengine.common.vec.Word2VEC;
import cn.edu.zucc.caviar.searchengine.common.vec.domain.WordEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Synonym {

    @Autowired
    public Word2VEC w2;


    public Map<String,Double> getSynonym(String token){
        Map<String,Double> synonymMaps = new HashMap<>();
        for(WordEntry wordEntry:w2.distance(token)){
            System.out.print(wordEntry.name+" ");
            System.out.print(wordEntry.score+" ");
            synonymMaps.put(wordEntry.name,Double.valueOf(wordEntry.score));
            if(wordEntry.score<0.7)
                break;
        }
        return synonymMaps;
    }

    public static void main(String args[]) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        Synonym synonym = applicationContext.getBean(Synonym.class);
        System.out.println("\n");
        System.out.println(synonym.getSynonym("西湖").keySet().size());
    }
}

