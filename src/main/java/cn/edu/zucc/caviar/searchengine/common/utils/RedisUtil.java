package cn.edu.zucc.caviar.searchengine.common.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.*;


@Component
public class RedisUtil {

    public final static String SEARCH_RESULT = "SEARCH_RESULT";

    public static Jedis getJedis() {
        return jedis;
    }

    public static void setJedis(Jedis jedis) {
        RedisUtil.jedis = jedis;
    }

    public static void setShardedJedis(ShardedJedis shardedJedis) {
        RedisUtil.shardedJedis = shardedJedis;
    }

    private static Jedis jedis;
    private static ShardedJedis shardedJedis;
    private static ShardedJedisPipeline pipeline;

    public RedisUtil() {
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.select(0);
        shardedJedis = getShardedJedis();
        pipeline = shardedJedis.pipelined();
    }

    /***
     * 建立索引
     * @param token 关键字
     * @param docId Hbase 二级索引
     * @param score 关键字在 Doc 中的特征分
     */
    public void insertIndex(String token, String docId, double score) {
        pipeline.zadd(token, score, docId);
    }


    /***
     * 搜索
     * @param queryMap
     * @return
     */
    public Collection<String> searchTokens(Map<String, Double> queryMap) {

        Collection<String> docSets = new TreeSet<String>();
        boolean empty = true;
        for (String token : queryMap.keySet()) {
            if (empty) {
                jedis.zunionstore(SEARCH_RESULT, SEARCH_RESULT, token);
                empty = false;
            } else {
                jedis.zinterstore(SEARCH_RESULT, SEARCH_RESULT, token);
            }
        }
        docSets.addAll(jedis.zrangeByScore(SEARCH_RESULT, 0, 1000));
        jedis.zremrangeByScore(SEARCH_RESULT, 0, 100);

        return docSets;
    }


    /***
     * 分页
     * @param currentPage 当前第几页
     * @param count 每页的内容数量
     * @return 返回元组遍历通过getElement 获取文章ID
     */
    public Set<Tuple> resultPaging(int currentPage, int count) {
        return jedis.zrevrangeByScoreWithScores(SEARCH_RESULT, 10, 0, currentPage * (count - 1), count);
    }

    /***
     * 包含同义词处理的搜索
     * @param queryMap <token:<synoym:score>>
     * @return
     */
    public Collection<String> searchTokensWithSynonym(Map<String, Map<String, Double>> queryMap) {

        Collection<String> docSets = new TreeSet<String>();
        jedis.zremrangeByScore("searchResult", 0, 100);
        jedis.zremrangeByScore("synonymResult", 0, 100);

        boolean empty = true;
        for (String token : queryMap.keySet()) {
            for (String synonym : queryMap.get(token).keySet()) {
                jedis.zunionstore("synonymResult", "synonymResult", synonym);
            }
            if (empty) {
                jedis.zunionstore("searchResult", "searchResult", "synonymResult");
                empty = false;
            } else {
                jedis.zinterstore("searchResult", "searchResult", "synonymResult");
            }
        }
        docSets.addAll(jedis.zrangeByScore("searchResult", 0, 1000));

        return docSets;
    }

    /***
     * redis 连接池
     * @return
     */
    public static ShardedJedis getShardedJedis() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(2);
        poolConfig.setMaxIdle(1);
        poolConfig.setMaxWaitMillis(2000);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnReturn(false);
        JedisShardInfo info1 = new JedisShardInfo("127.0.0.1", 6379);
        JedisShardInfo info2 = new JedisShardInfo("127.0.0.1", 6379);
        ShardedJedisPool pool = new ShardedJedisPool(poolConfig, Arrays.asList(info1, info2));
        return pool.getResource();
    }

    /***
     *
     * @param token
     * @param low
     * @param high
     * @return
     */
    public List<String> searchByScore(String token, String low, String high) {
        List<String> spellCheckSets = new ArrayList<String>();
        spellCheckSets.addAll(jedis.zrangeByScore(token, Double.valueOf(low), Double.valueOf(high)));
        return spellCheckSets;
    }

    /***
     * 批量写
     */
    public static void pipeLineSync() {
        pipeline.sync();
        shardedJedis.close();
    }


    public void test() {

        Collection<Tuple> rs = jedis.zrangeByScoreWithScores("world", 0, 20);


        for (Tuple title : rs) {
            System.out.println(title.getElement());
        }
    }

    public static void main(String args[]) {
        RedisUtil util = new RedisUtil();

        Set<Tuple> list = jedis.zrevrangeByScoreWithScores("杭州", 10, 0, 20, 10);
        for (Tuple t : list) {
            System.out.println(t.getElement());
        }

    }

}