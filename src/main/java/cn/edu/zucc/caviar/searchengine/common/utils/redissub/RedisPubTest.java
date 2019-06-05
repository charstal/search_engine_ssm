package cn.edu.zucc.caviar.searchengine.common.utils.redissub;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 * 发布者
 * @ClassName: RedisPubTest
 * @Description: TODO
 * @author OnlyMate
 * @Date 2018年8月23日 下午2:59:25
 *
 */
public class RedisPubTest {
    @Test
    public void pubjava() {
        System.out.println("发布者 ");
        Jedis jr = null;
        try {
            jr = new Jedis("127.0.0.1", 6379, 0);// redis服务地址和端口号
            // jr客户端配置监听两个channel
            jr.publish( "news.share", "新闻分享");
            jr.publish( "news.blog", "新闻博客");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jr != null) {
                jr.disconnect();
            }
        }
    }
}