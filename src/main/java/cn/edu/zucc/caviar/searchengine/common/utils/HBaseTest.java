package cn.edu.zucc.caviar.searchengine.common.utils;

import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HBaseTest {

    @Autowired
    private HbaseTemplate template;

    @Test
    public Document get(String docId) {
        Document doc = template.get("doc", docId, new RowMapper<Document>() {
            @Override
            public Document mapRow(Result result, int rowNum) throws Exception {
                List<Cell> listCell = result.listCells();
                Document doc = new Document();

                if (listCell != null && listCell.size() > 0) {
                    for (Cell cell : listCell) {
                        String colName = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());

                        String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                        if (colName.equals("content"))
                            doc.setContent(value);
                        else if (colName.equals("author"))
                            doc.setAuthor(value);
                        else if (colName.equals("title"))
                            doc.setTitle(value);
                        else if (colName.equals("likeCount"))
                            doc.setLikeCount(Integer.parseInt(value));
                        else if (colName.equals("shareCount"))
                            doc.setShareCount(Integer.parseInt(value));
                        else if (colName.equals("favoriteCount"))
                            doc.setFavoriteCount(Integer.parseInt(value));
                        else if (colName.equals("publishDate"))
                            doc.setPublishDate(value);
                        else if (colName.equals("imageUrls")) {
                            String urls[] = value.split(",");
                            List<String> images = Arrays.asList(urls);
                            doc.setImageUrls(images);
                        }
                    }
                }
                return doc;
            }
        });
        doc.setDocId(docId);
        return doc;
    }


//    public List<Document> get(String docId){
//        Document doc = template.get("doc", docId, new RowMapper<Document>() {
//            @Override
//            public Document mapRow(Result result, int rowNum) throws Exception {
//                List<Cell> listCell = result.listCells();
//                Document doc = new Document();
//
//                if (listCell != null && listCell.size() > 0) {
//                    for (Cell cell : listCell) {
//                        String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
//
//                        String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
//                        if(colName.equals("content"))
//                            doc.setContent(value);
//                        else if(colName.equals("author"))
//                            doc.setAuthor(value);
//                        else if(colName.equals("likeCount"))
//                            doc.setLikeCount(Integer.parseInt(value));
//                        else if(colName.equals("shareCount"))
//                            doc.setShareCount(Integer.parseInt(value));
//                        else if(colName.equals("favoriteCount"))
//                            doc.setFavoriteCount(Integer.parseInt(value));
//                        else if(colName.equals("imageUrls")){
//                            String urls[] = value.split(",");
//                            List<String > images = Arrays.asList(urls);
//                            doc.setImageUrls(images);
//                        }
//                    }
//                }
//                return doc;
//            }
//        });
//        doc.setDocId(docId);
//        return doc;
//    }

    @Test
    public void testFind() {
        List<String> rows = template.find("doc", "cf1", "name", new RowMapper<String>() {

            @Override
            public String mapRow(Result result, int i) throws Exception {
                return result.toString();
            }

        });
    }


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        HBaseTest hBaseTest = applicationContext.getBean(HBaseTest.class);
        long t = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Document doc = hBaseTest.get("5c7d187f000000000f007b11");
            System.out.println(doc.getContent());
            if (i == 0)
                t = System.currentTimeMillis();
        }
        System.out.println(System.currentTimeMillis() - t);
    }
}