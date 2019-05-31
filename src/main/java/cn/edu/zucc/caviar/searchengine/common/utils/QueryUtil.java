//package cn.edu.zucc.caviar.searchengine.common.utils;
//
//
//import cn.edu.zucc.caviar.searchengine.common.query.segment.ChineseSegmentation;
//import cn.edu.zucc.caviar.searchengine.common.query.spell.SpellingChecker;
//import cn.edu.zucc.caviar.searchengine.common.query.synonym.Synonym;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//
//
//public class QueryUtil {
//
//    private Synonym synonymControl;
//    private RedisUtil redisUtil;
//    private HbaseUtil hbaseUtil;
//    private SpellingChecker spellingChecker;
//
//    public QueryUtil() {
//        synonymControl = new Synonym();
//        redisUtil = new RedisUtil();
//        hbaseUtil = new HbaseUtil();
//        spellingChecker = new SpellingChecker();
//    }
//
////    public List<String> query(String queryString){
////        List<String> docs = new ArrayList<>();
////        List<String> keywords = ChineseSegmentation.keywordsSegmentaion(queryString);
////
////        Map<String, Map<String,Double>> searchMap = new HashMap<>();
////        for(String keyword : keywords)
////        {
////            if(keyword.matches("[a-zA-Z]+"))
////                searchMap.put(keyword,spellingChecker.checkSpell(keyword,true));
////            else
////                searchMap.put(keyword,synonymControl.getSynonym(keyword));
////        }
////
////        docs.addAll(redisUtil.searchTokensWithSynonym(searchMap));
////        return docs;
////    }
//
////    public static  void main(String args[]){
////        QueryUtil queryUtil = new QueryUtil();
////
////        Scanner scanner = new Scanner(System.in);
////        while(true){
////            List<String> docIds = queryUtil.query(scanner.next());
////            for(String doc:docIds){
////                System.out.println(doc);
////
//////            try {
//////                System.out.println(queryUtil.hbaseUtil.getDataByRowKey(doc).getContent());
//////            } catch (IOException e) {
//////                e.printStackTrace();
//////            }
////            }
////        }
////
////    }
//
//}
