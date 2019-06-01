package cn.edu.zucc.caviar.searchengine.core.service.impl;

import cn.edu.zucc.caviar.searchengine.common.query.segment.ChineseSegmentation;
import cn.edu.zucc.caviar.searchengine.common.query.spell.PinyinUtil;
import cn.edu.zucc.caviar.searchengine.common.query.spell.SoundexCoder;
import cn.edu.zucc.caviar.searchengine.common.query.synonym.Synonym;
import cn.edu.zucc.caviar.searchengine.common.utils.HBaseTest;
import cn.edu.zucc.caviar.searchengine.common.utils.RedisTest;
import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.pojo.Response;
import cn.edu.zucc.caviar.searchengine.core.service.SearchService;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

//    @Autowired
//    QueryUtil queryUtil;

    @Autowired
    private RedisTest redisUtil;

    @Autowired
    private HBaseTest hbaseUtil;

    @Autowired
    private Synonym synonymUtil;


    @Override
    public Response keywordSearch(String keyword, Integer page) {
        Response response = new Response();
        if (page == 1) {
            response.setDocumentNumber((int) documentPageCount(keyword));
        }
        response.setDocumentSet(documentsInPage(page, 10));

        return response;
    }

    /***
     * 查询，返回文章数
     * @param queryString
     * @return
     */
    @Override
    public long documentPageCount(String queryString) {

        List<String> keywords = ChineseSegmentation.keywordsSegmentaion(queryString);
        Set<Document> documents = new HashSet<>();

        Map<String, Map<String,Double>> searchMap = new HashMap<>();
        for(String keyword : keywords)
        {
            if(keyword.matches("[a-zA-Z]+"))
                searchMap.put(keyword,checkSpell(keyword,true));
            else
                searchMap.put(keyword,synonymUtil.getSynonym(keyword));
        }

        redisUtil.searchTokensWithSynonym(searchMap);
        return redisUtil.zsetSize();
    }



    /***
     * 分页支持
     * @param currentPage 当前第几页(从第一页开始)
     * @param eachPageDocumentsCount 每页的内容数量
     * @return
     */
    public Set<Document> documentsInPage(long currentPage,long eachPageDocumentsCount){
        Set<Document> documents = new HashSet<>();

        Set<Object> docIds = redisUtil.resultPagingWithScores(currentPage, eachPageDocumentsCount);
        for(Object docId:docIds){
            documents.add(hbaseUtil.get((String)docId));
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
            similarSpellTokens.addAll(redisUtil.searchByScore("n",soundexCode,soundexCode));
            similarSpellTokens.addAll(redisUtil.searchByScore("l",soundexCode,soundexCode));
        }
        else if(initial.equals("zh")||initial.equals("z")){
            similarSpellTokens.addAll(redisUtil.searchByScore("zh",soundexCode,soundexCode));
            similarSpellTokens.addAll(redisUtil.searchByScore("z",soundexCode,soundexCode));
        }
        else if(initial.equals("j")||initial.equals("q")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",soundexCode,soundexCode));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",soundexCode,soundexCode));
        }

        else if(initial.equals("s")||initial.equals("sh")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",soundexCode,soundexCode));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",soundexCode,soundexCode));
        }
        else if(initial.equals("c")||initial.equals("ch")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",soundexCode,soundexCode));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",soundexCode,soundexCode));
        }
        else if(initial.equals("g")||initial.equals("k")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",soundexCode,soundexCode));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",soundexCode,soundexCode));
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
