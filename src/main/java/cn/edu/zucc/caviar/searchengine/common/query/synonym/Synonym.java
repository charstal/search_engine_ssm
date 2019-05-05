package cn.edu.zucc.caviar.searchengine.common.query.synonym;

import cn.edu.zucc.caviar.searchengine.common.vec.Word2VEC;
import cn.edu.zucc.caviar.searchengine.common.vec.domain.WordEntry;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Synonym {

    public Word2VEC w2;

    /***
     * 模型所在地址
     */
    public Synonym(){
        w2 = new Word2VEC();
        //加载模型
        try {
            w2.loadGoogleModel("/home/easybritney/idea-IU-191.6183.87/IdeaProjects/src/main/java/com/carviar/searchEngines/query/synonym/Google_word2vec_zhwiki1710_300d.bin") ;
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

