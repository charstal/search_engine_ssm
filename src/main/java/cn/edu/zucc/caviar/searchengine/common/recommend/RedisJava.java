package cn.edu.zucc.caviar.searchengine.common.recommend;

import redis.clients.jedis.*;

import java.util.*;


public class RedisJava {

    public final static String SEARCH_RESULT = "SEARCH_RESULT";
    public static Jedis getJedis() {
        return jedis;
    }

    public static void setJedis(Jedis jedis) {
        RedisJava.jedis = jedis;
    }

    public static void setShardedJedis(ShardedJedis shardedJedis) {
        RedisJava.shardedJedis = shardedJedis;
    }

    private static Jedis jedis;
    private static ShardedJedis shardedJedis;
    private static ShardedJedisPipeline pipeline;

    public RedisJava(){
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.select(0);
        shardedJedis = getShardedJedis();
        pipeline = shardedJedis.pipelined();
    }

    /***
     * 建立索引
     * @param docId 文档id
     * @param item  关联文档id
     * @param score 关联分
     */
    public void insertIndex(String docId,String item,double score){
        pipeline.zadd(docId,score,item);
    }


    /***
     * redis 连接池
     * @return
     */
    public static ShardedJedis getShardedJedis(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxTotal(2);
        poolConfig.setMaxIdle(1);
        poolConfig.setMaxWaitMillis(2000);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnReturn(false);
        JedisShardInfo info1 = new JedisShardInfo("127.0.0.1",6379);
        JedisShardInfo info2 = new JedisShardInfo("127.0.0.1",6379);
        ShardedJedisPool pool = new ShardedJedisPool(poolConfig, Arrays.asList(info1,info2));
        return pool.getResource();
    }

    public static void pipeLineSync(){
        pipeline.sync();
        shardedJedis.close();
    }

    public  Set<String> searchByDocId(String DocId,int low,int high){
        Set<Tuple> RecommendDocId = jedis.zrevrangeByScoreWithScores(DocId,high,low);
        List<String> RecommendDocIdlist=new ArrayList<String>();
        Set<String> Answer=new HashSet<String>();
        int number=0;
        for(Tuple t:RecommendDocId){
            if(number++<=10)
                RecommendDocIdlist.add(t.getElement());
            else break;
//            System.out.println(t.getElement());
//            System.out.println(t.getScore());
        }
        while (Answer.size()<=5) {
            Random random = new Random();
            int n = random.nextInt(RecommendDocIdlist.size());
            Answer.add(RecommendDocIdlist.get(n));
        }
        return Answer;
    }

    public static void main(String args[]){
        RedisJava redisJava=new RedisJava();
        Set<String> Ans=redisJava.searchByDocId("5b8c9b9607ef1c64a999dbda",0,1);
        System.out.println("数量："+Ans.size());
        for(String s:Ans){
            System.out.println(s);
        }
    }
}