package cn.edu.zucc.caviar.searchengine.common.recommend;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;


public class ArticleJsonParser {

    public static RedisJava redisUtil;

    /***
     * 按行读取doc Json数据并存入redis
     * @param filePath json所在目录
     */
    public static void readContentJSON(String filePath){
        JsonParser parser = new JsonParser();
        JsonObject object;

        try {
            String root = System.getProperty("user.dir");
            filePath = root + "/src/main/resources/" + filePath;
            BufferedReader bfReader = new BufferedReader(new FileReader(filePath));
            String line ;
            while((line = bfReader.readLine())!=null){
                object = (JsonObject) parser.parse(line);
                storeContentInRedis(object);
            }
            RedisJava.pipeLineSync();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    /***
     * 保存Json格式的doc入redis
     * @param article
     */
    public static void storeContentInRedis(JsonObject article){
        String docId = article.get("id").getAsString();
//        System.out.println(docId);
        JsonObject scoreArray = article.get("sim").getAsJsonObject();
        for(String item :scoreArray.keySet()){
            double score=scoreArray.get(item).getAsDouble();
//            System.out.println("item:"+item+"   score:"+score);
            redisUtil.insertIndex(docId,item,score);
        }
    }

    public static void main(String args[]){
        redisUtil=new RedisJava();
        readContentJSON("search_data/Similar.json");
    }
}
