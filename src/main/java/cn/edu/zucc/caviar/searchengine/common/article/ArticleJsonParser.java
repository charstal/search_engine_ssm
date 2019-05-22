package cn.edu.zucc.caviar.searchengine.common.article;

import cn.edu.zucc.caviar.searchengine.common.query.spell.PinyinUtil;
import cn.edu.zucc.caviar.searchengine.common.query.spell.SoundexCoder;
import cn.edu.zucc.caviar.searchengine.common.utils.HbaseUtil;
import cn.edu.zucc.caviar.searchengine.common.utils.RedisUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class ArticleJsonParser {

    public static HbaseUtil hbaseUtil;
    public static RedisUtil redisUtil;
    public static final String SAVE_CONTENT = "Content";
    public static final String CREATE_INDEX = "Index";

    /***
     * 按行读取doc Json数据并存入hbase
     * @param filePath json所在目录
     * @param operate 操作 建立内容 或 建立索引
     */
    public static void readContentJSON(String filePath, String operate) {
        JsonParser parser = new JsonParser();
        JsonObject object;

        try {
            String root = System.getProperty("user.dir");
            filePath = root + "/src/main/resources/" + filePath;

            BufferedReader bfReader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = bfReader.readLine()) != null) {
                object = (JsonObject) parser.parse(line);
                if (operate.equals(SAVE_CONTENT))
                    storeContentInHbase(object);
                else if (operate.equals(CREATE_INDEX))
                    createIndex(object);

            }
            RedisUtil.pipeLineSync();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /***
     * 保存Json格式的doc入hbase
     * @param article
     */
    public static void storeContentInHbase(JsonObject article) {

        JsonObject docData = article.getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
        String docId = docData.get("id").getAsString();
        String author = docData.get("posterScreenName").getAsString();

        System.out.println(docId);

        String content = docData.get("content").getAsString();

        String favoriteCount = docData.get("favoriteCount").getAsJsonObject().get("$numberInt").getAsString();
        String likeCount = docData.get("likeCount").getAsJsonObject().get("$numberInt").getAsString();
        String commentCount = docData.get("commentCount").getAsJsonObject().get("$numberInt").getAsString();
        String shareCount = docData.get("shareCount").getAsJsonObject().get("$numberInt").getAsString();
        JsonArray images = docData.get("imageUrls").getAsJsonArray();
        String publishDate = docData.get("publishDateStr").getAsString();
//        System.out.println(publishDate);

        String imageUrls = "";

        for (int i = 0; i < images.size(); i++) {
            if (i == 0)
                imageUrls += images.get(i).getAsString();
            else
                imageUrls += ("," + images.get(i).getAsString());
        }
        hbaseUtil.put(docId, "imageUrls", imageUrls);
        hbaseUtil.put(docId, "docId", docId);
        hbaseUtil.put(docId, "author", author);
        hbaseUtil.put(docId, "content", content);
        hbaseUtil.put(docId, "favoriteCount", favoriteCount);
        hbaseUtil.put(docId, "likeCount", likeCount);
        hbaseUtil.put(docId, "commentCount", commentCount);
        hbaseUtil.put(docId, "shareCount", shareCount);
        hbaseUtil.put(docId, "publishDate", publishDate);
    }

    public static void createIndex(JsonObject keyWords) {
        String docId = keyWords.get("id").getAsString();
        for (String keyword : keyWords.get("keywords").getAsJsonObject().keySet()) {
            double score = keyWords.get("keywords").getAsJsonObject().get(keyword).getAsDouble();
            System.out.println(keyword);
            List<Pinyin> pinyinList = HanLP.convertToPinyinList(keyword);
            String soundexCode = SoundexCoder.soundex(pinyinList);
            System.out.println(score);
            redisUtil.insertIndex(keyword, docId, score);
            System.out.println(PinyinUtil.getPinyin(pinyinList));
            redisUtil.insertIndex(pinyinList.get(0).getShengmu().toString(), keyword, Double.valueOf(soundexCode));
        }

    }

    /***
     * 建立索引以及存储文章
     * @param args
     */
    public static void main(String args[]) {
        redisUtil = new RedisUtil();
        hbaseUtil = new HbaseUtil();

        readContentJSON("search_data/keywords(1).json", CREATE_INDEX);
        readContentJSON("search_data/xhs_note_item_final.json", SAVE_CONTENT);
    }
}
