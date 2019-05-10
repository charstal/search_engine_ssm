package cn.edu.zucc.caviar.searchengine.core.service.impl;

import cn.edu.zucc.caviar.searchengine.common.query.segment.ChineseSegmentation;
import cn.edu.zucc.caviar.searchengine.common.query.spell.PinyinUtil;
import cn.edu.zucc.caviar.searchengine.common.query.spell.SoundexCoder;
import cn.edu.zucc.caviar.searchengine.common.query.spell.SpellingChecker;
import cn.edu.zucc.caviar.searchengine.common.query.synonym.Synonym;
import cn.edu.zucc.caviar.searchengine.common.utils.HBaseTest;
import cn.edu.zucc.caviar.searchengine.common.utils.QueryUtil;
import cn.edu.zucc.caviar.searchengine.common.utils.RedisTest;
import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.service.SearchService;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

//    @Autowired
//    QueryUtil queryUtil;

    @Autowired
    private RedisTest redisUtil;

    @Autowired
    private HBaseTest hbaseUtil;


    private Synonym synonymUtil;




    @Override
    public List<Document> keywordSearch(String queryString) {

        List<Object> docs = new ArrayList<>();
        List<Document> documents = new ArrayList<>();
        List<String> keywords = ChineseSegmentation.keywordsSegmentaion(queryString);

        Map<String, Map<String,Double>> searchMap = new HashMap<>();
        for(String keyword : keywords)
        {
            if(keyword.matches("[a-zA-Z]+"))
                searchMap.put(keyword,checkSpell(keyword,true));
            else
                searchMap.put(keyword,synonymUtil.getSynonym(keyword));
        }

        docs.addAll(redisUtil.searchTokensWithSynonym(searchMap));
        for(Object doc :docs){
            documents.add(hbaseUtil.get((String)doc));
        }
        return documents;
    }



    /***
     * 查询相同SoundexCode的字符串
     * @param token 要检查的内容
     * @param isPinyin 检查内容是否为拼音
     * @return
     */


    public Map<String, Double> checkSpell(String token,boolean isPinyin){


        List<Object> similarSpellTokens = new ArrayList<Object>();
        Map<String ,Double> checkMap = new HashMap<>();
        String pinyinString="";

        String soundexCode= "";
        String initial = "";

        if(isPinyin)
        {
            pinyinString = token;
            soundexCode = SoundexCoder.soudex(token);
            initial = token.substring(0,1);
        }
        else
        {
            List<Pinyin> pinyin = HanLP.convertToPinyinList(token);
            pinyinString = PinyinUtil.getPinyin(pinyin);
            initial = pinyin.get(0).getShengmu().toString();
            soundexCode = SoundexCoder.soundex(pinyin);
        }


        if(initial.equals("n")||initial.equals("l")||initial.equals("r")){
            similarSpellTokens.addAll(redisUtil.searchByScore("n",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("l",pinyinString,pinyinString));
        }
        else if(initial.equals("zh")||initial.equals("z")){
            similarSpellTokens.addAll(redisUtil.searchByScore("zh",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("z",pinyinString,pinyinString));
        }
        else if(initial.equals("j")||initial.equals("q")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",pinyinString,pinyinString));
        }

        else if(initial.equals("s")||initial.equals("sh")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",pinyinString,pinyinString));
        }
        else if(initial.equals("c")||initial.equals("ch")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",pinyinString,pinyinString));
        }
        else if(initial.equals("g")||initial.equals("k")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",pinyinString,pinyinString));
        }
        else
        {
            similarSpellTokens.addAll(redisUtil.searchByScore(initial,soundexCode,soundexCode));
        }
        for(Object checkToken:similarSpellTokens){
            checkMap.put((String)checkToken,0.6);
        }

        return checkMap;
    }
}
