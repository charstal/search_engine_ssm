package cn.edu.zucc.caviar.searchengine.common.query.synonym;

import cn.edu.zucc.caviar.searchengine.common.vec.Word2VEC;
import cn.edu.zucc.caviar.searchengine.common.vec.domain.WordEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Synonym {

    @Autowired
    public Word2VEC w2;

    /***
     * 模型所在地址
     */
    public Synonym(){

        //加载模型
        try {
            String root = System.getProperty("user.dir");
            String filePath = root + "/src/main/resources/search_data/" + "Google_word2vec_zhwiki1710_300d.bin";
//            System.out.println(filePath);
            w2.loadGoogleModel(filePath) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        new Synonym().getSynonym("甜点");
    }
}

