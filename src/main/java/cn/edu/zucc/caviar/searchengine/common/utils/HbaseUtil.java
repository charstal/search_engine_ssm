package cn.edu.zucc.caviar.searchengine.common.utils;

import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HbaseUtil {
    /**
     * HBASE 表名称
     */
    public  final static String TABLE_NAME = "doc";
    /**
     * 列簇1 文章信息
     */
    public  final static String COLUMNFAMILY_1 = "info";
    /**
     * 列簇1中的列
     */

    public  final static String COLUMNFAMILY_1_AUTHOR = "author";
    public  final static String COLUMNFAMILY_1_CONTENT = "content";
    public  final static String COLUMNFAMILY_1_DESCRIBE = "describe";
    public  final static String COLUMNFAMILY_1_COLLECT_COUNT = "collectCount";

    public static Admin admin = null;
    public static Configuration conf = null;
    public static Connection conn = null;


    public HbaseUtil() {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "localhost:2181");
        conf.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        try {
            conn = ConnectionFactory.createConnection(conf);
            admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /***
     * 往hbase添加记录
     * @param row
     * @param column
     * @param data
     */
    public void put(String row,String column,String data){
        Table table = null;
        try{
            table = conn.getTable(TableName.valueOf(TABLE_NAME));
            Put putData = new Put(Bytes.toBytes(row));
            putData.addColumn(COLUMNFAMILY_1.getBytes(),column.getBytes(),data.getBytes());
            table.put(putData);

        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /***
     * 建表
     */
    public void createTable(){

        TableName name = TableName.valueOf(TABLE_NAME);

        try {
            if(admin.tableExists(name)){
                System.out.println("Table exists");
            }
            else{
                HTableDescriptor tableDescriptor = new HTableDescriptor(TABLE_NAME);
                tableDescriptor.addFamily(new HColumnDescriptor(COLUMNFAMILY_1));
                admin.createTable(tableDescriptor);
                System.out.println("Succeed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /***
     * 根据docID 查询文章
     * @param rowKey docID
     * @return
     * @throws IOException
     */
    public Document getDataByRowKey(String rowKey) throws IOException {

        Table table = conn.getTable(TableName.valueOf(TABLE_NAME));
        Get get = new Get(rowKey.getBytes());
        Document doc = new Document();
        doc.setDocId(rowKey);
        //先判断是否有此条数据
        if(!get.isCheckExistenceOnly()){
            Result result = table.get(get);
            for (Cell cell : result.rawCells()){
                String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                if(colName.equals("content")){
                    doc.setContent(value);
                }
                if(colName.equals("author")){
                    doc.setAuthor(value);
                }
            }
        }
        return doc;
    }

    /***
     * 获取制定文章的制定内容
     * @param rowKey
     * @param col
     * @return
     */
    public  String getCellData(String rowKey, String col){

        try {
            Table table = conn.getTable(TableName.valueOf(TABLE_NAME));
            String result = null;
            Get get = new Get(rowKey.getBytes());
            if(!get.isCheckExistenceOnly()){
                get.addColumn(Bytes.toBytes(COLUMNFAMILY_1),Bytes.toBytes(col));
                Result res = table.get(get);
                byte[] resByte = res.getValue(Bytes.toBytes(COLUMNFAMILY_1), Bytes.toBytes(col));
                return result = Bytes.toString(resByte);
            }else{
                return result = "查询结果不存在";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
    public static void main(String args[]){
//        new HbaseUtil().createTable();
        System.out.println(new HbaseUtil().getCellData("5c7d187f000000000f007b11","content"));
    }

}
