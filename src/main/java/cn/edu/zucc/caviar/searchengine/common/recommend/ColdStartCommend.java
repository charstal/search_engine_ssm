package cn.edu.zucc.caviar.searchengine.common.recommend;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ColdStartCommend {
    public static RedisJava redisUtil;

    /**读取csv文件
     * @param filePath 文件路径
     * @param headers csv列头
     * @return CSVRecord 列表
     * @throws IOException **/
    public static List<CSVRecord> readCSV(String filePath, String[] headers) throws IOException {

        //创建CSVFormat
        CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers);

        FileReader fileReader=new FileReader(filePath);

        //创建CSVParser对象
        CSVParser parser=new CSVParser(fileReader,formator);

        List<CSVRecord> records=parser.getRecords();

        parser.close();
        fileReader.close();

        return records;
    }


    public static void storeContentInRedis(List<CSVRecord> csvRecords){
        if(csvRecords != null) {
            int size = csvRecords.size();
            for(int i = 1; i < size; ++i) {
                redisUtil.docBaseScoreInsert(csvRecords.get(i).get("id"), Double.valueOf(csvRecords.get(i).get("score")));

//                redisUtil.insertIndex("cold_start",
//                        csvRecords.get(i).get("id"),
//                        Double.valueOf(csvRecords.get(i).get("score")));
//                System.out.println("------------------------------------------");
//                System.out.println("id:" + csvRecords.get(i).get("id"));
//                System.out.println("score:" + csvRecords.get(i).get("score"));
//                System.out.println("date:" + csvRecords.get(i).get("date"));
//                System.out.println("------------------------------------------");
            }
        }

    }



    public static void main(String[] args) throws IOException {
        String headers[] = new String[] {"id",
                "comment_count",
                "like_count",
                "favorite_count",
                "share_count",
                "date",
                "keywords",
                "score"
        };
        String root = System.getProperty("user.dir");

        String csvPath = "search_data/xhs_cold_recommend.csv";
        String filePath = root + "/src/main/resources/" + csvPath;
        List<CSVRecord> csvRecords = readCSV(filePath, headers);


        redisUtil = new RedisJava();
        storeContentInRedis(csvRecords);

    }

}
