package cn.edu.zucc.caviar.searchengine.common.utils;

import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.nio.DoubleBuffer;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTest {

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public final static String SEARCH_RESULT = "SEARCH_RESULT";

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    private void expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }



    //============================index===========================================

    /***
     *
     * @param token
     * @param low
     * @param high
     * @return
     */
    public List<Object> searchByScore(String token, String low, String high) {
        List<Object> spellCheckSets = new ArrayList<Object>();
        redisTemplate.opsForZSet().rangeByScore(token,Double.valueOf(low),Double.valueOf(high));

        spellCheckSets.addAll(redisTemplate.opsForZSet().rangeByScore(token, Double.valueOf(low), Double.valueOf(high)));
        return spellCheckSets;
    }

    public boolean zadd(String token, String docId, double score) {
        try {
            redisTemplate.opsForZSet().add(token, docId, score);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /***
     * 分页
     * @param currentPage 当前第几页(从第一页开始)
     * @param eachPageDocumentsCount 每页的内容数量
     * @return 返回元组遍历通过getElement 获取文章ID
     */
    public Set<Object> resultPagingWithScores(long currentPage, long eachPageDocumentsCount) {
        return redisTemplate.opsForZSet().reverseRangeByScore(SEARCH_RESULT, 0, 100,(currentPage -1)* eachPageDocumentsCount, eachPageDocumentsCount);

    }

    /***
     * 包含同义词处理的搜索
     * @param queryMap <token:<synoym:score>>
     * @return
     */
    public Collection<Object> searchTokensWithSynonym(Map<String, Map<String, Double>> queryMap) {

        Collection<Object> docSets = new TreeSet<Object>();
        redisTemplate.opsForZSet().removeRangeByScore(SEARCH_RESULT, 0, 100);
        redisTemplate.opsForZSet().removeRangeByScore("synonymResult", 0, 100);

        boolean empty = true;
        for (String token : queryMap.keySet()) {
            for (String synonym : queryMap.get(token).keySet()) {
                redisTemplate.opsForZSet().unionAndStore("synonymResult",  synonym,"synonymResult");
            }
            redisTemplate.opsForZSet().unionAndStore("synonymResult",  token , "synonymResult");
            if (empty) {
                redisTemplate.opsForZSet().unionAndStore(SEARCH_RESULT, "synonymResult", SEARCH_RESULT);
                empty = false;
            } else {
                redisTemplate.opsForZSet().unionAndStore(SEARCH_RESULT, "synonymResult", SEARCH_RESULT); //intersectAndStore()
            }
        }

        return docSets;
    }

    public Set<String> searchToken(List<String> tokenList) {
        Set<String> docIdSet = new HashSet<>();

        redisTemplate.opsForZSet().removeRange("cold-start-recommend", 0, -1);

        for(String a: tokenList) {
            Set<Object> set = redisTemplate.opsForZSet().reverseRangeByScore(a, 0, 100, 0, 10);


            for(Object b: set) {
                String docId = b.toString();
//                System.out.println(b.toString());

                Object clickObject = redisTemplate.opsForValue().get("click:" + b.toString());
                double clickValue = 0;
                if(clickObject != null && clickObject.toString() != null) {
                    System.out.println(clickObject.toString());
                    clickValue = Double.valueOf(clickObject.toString());
                }
//                System.out.println("clickValue:" + clickValue);
                double baseScore = Double.valueOf(redisTemplate.opsForValue().get("cold-start-base-score:" + b.toString()).toString());
//                System.out.println("baseScore:" + baseScore);


                double sumScore = 0.0;
                if(clickValue != 0) {
                    sumScore = clickValue * 0.35 + baseScore / 5 * 0.65;
                } else {
                    sumScore = baseScore / 5 * 0.65;
                }
                this.zadd("cold-start-recommend", docId, sumScore);
            }

        }

        Set<Object> coldStartRecommend = redisTemplate.opsForZSet().reverseRangeByScore("cold-start-recommend", 0, 1000000, 0, 6);

        for(Object a: coldStartRecommend) {
//            System.out.println(a.toString());

            docIdSet.add(a.toString());
        }



        return docIdSet;
    }


    public  Set<String> searchSimilarDocId(String DocId,int low,int high){

        Set<ZSetOperations.TypedTuple<Object>> RecommendDocId = zrevrangeByScoreWithScores(DocId,high,low);
        List<String> RecommendDocIdlist=new ArrayList<String>();
        Set<String> Answer=new HashSet<String>();
        int number=0;
        for(ZSetOperations.TypedTuple<Object> t:RecommendDocId){
            if(number++<=10)
                RecommendDocIdlist.add((String) t.getValue());
            else break;
//            System.out.println(t.getElement());
//            System.out.println(t.getScore());
        }
        while (RecommendDocIdlist.size() != 0 && Answer.size()<=5) {
            Random random = new Random();
            int n = random.nextInt(RecommendDocIdlist.size());
            Answer.add(RecommendDocIdlist.get(n));
        }
        return Answer;
    }


    public Set<String> recommendDocId(String recommendNumber) {
        List<Object> res = redisTemplate.opsForList().range(recommendNumber, 0, -1);
        Set<String> result = new HashSet<String>();

        for(Object a: res) {
            result.add(a.toString());
        }

        return result;

    }

    public void zunion(String key,String r1,String r2){
        redisTemplate.opsForZSet().unionAndStore(key, r1, r2);
    }

    public Set<Object> zrangebyscore() {
        return redisTemplate.opsForZSet().rangeByScore("杭州", 0, 100);
    }

    public long zsetSize(){
        return redisTemplate.opsForZSet().size(SEARCH_RESULT);
    }

    public Set<ZSetOperations.TypedTuple<Object>> zrevrangeByScoreWithScores(String topic, int low, int high) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(topic, high, low);
    }


    public Set<ZSetOperations.TypedTuple<Object>> zrevrange(String topic, int low, int high) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(topic, low, high);
    }

    public Double zincrby(String topic, String keyword, Double offset) {
        return redisTemplate.opsForZSet().incrementScore(topic, keyword, offset);
    }





    public List<String> hotpotList() {
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().reverseRangeWithScores("hotpot", 0, 9);
        List<String> list = new ArrayList<String>();

        for(ZSetOperations.TypedTuple<Object> a: set) {
            list.add((String) a.getValue());
        }

        return list;
    }


    public void clickIncr(String docId) {
        redisTemplate.opsForValue().increment("click:" + docId, 1);


    }


    public static void main(String args[]) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

//        HBaseTest hBaseTest = applicationContext.getBean(HBaseTest.class);
        RedisTest redisTest = applicationContext.getBean(RedisTest.class);
//        Set<String> Ans=redisTest.searchSimilarDocId("5b8c9b9607ef1c64a999dbda",0, 1);
//        System.out.println("数量："+Ans.size());
//
//        List<String> titleList = new ArrayList<>();
//        titleList.add(hBaseTest.get("5b8c9b9607ef1c64a999dbda").getTitle());
//
//        for(String s:Ans){
//            System.out.println(s);
//            titleList.add(hBaseTest.get(s).getTitle());
//        }
//
//        for(String a: titleList) {
//            System.out.println(a);
//        }
//
        List<String> list = new ArrayList<>();
        list.add("西湖");
//        list.add("杭州");
        Set<String> strings = redisTest.searchToken(list);

        for(String a: strings) {
            System.out.println(a);
        }


    }




}