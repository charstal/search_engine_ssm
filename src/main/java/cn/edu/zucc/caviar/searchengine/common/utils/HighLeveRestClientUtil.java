package cn.edu.zucc.caviar.searchengine.common.utils;
import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.stereotype.Component;

@Component
public class HighLeveRestClientUtil {

    RestHighLevelClient client;

    Logger logger ;


    public HighLeveRestClientUtil() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        logger = LogManager.getRootLogger();
    }

    public List<Document> puts = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        HighLeveRestClientUtil esUtil = new HighLeveRestClientUtil();
//        esUtil.addData("doc", "xhs", "dasdasddasd", "dasdasddasd", "", "打算发烦你的居然叫娜娜的缴纳时记得那句");
        List<Document> documents=esUtil.search("doc","xhs","甜食","title","content");
        System.out.println(documents.size());

        String indexName = "doc";
        String keyword = "hz";



        esUtil.client.close();
    }

    public List<Document> search(String index,String types,String query,String field1,String field2) {
        // 1、创建search请求
        //SearchRequest searchRequest = new SearchRequest();
        List<Document> documentsList=new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(types);
        // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //构造QueryBuilder
        MultiMatchQueryBuilder matchQueryBuilder = new MultiMatchQueryBuilder(query).field(field1,8).field(field2, 1);
//        QueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", "hangzhou")
//                .fuzziness(Fuzziness.AUTO)
//                .prefixLength(3)
//                .maxExpansions(10);
        sourceBuilder.query(matchQueryBuilder);
//        sourceBuilder.query(shouldTitleBool);


        sourceBuilder.from(0); //设置from选项，确定要开始搜索的结果索引。 默认为0。
        sourceBuilder.size(10000); //设置大小选项，确定要返回的搜索匹配数。 默认为10
        sourceBuilder.timeout(new TimeValue(30, TimeUnit.SECONDS));


        //设置返回哪些字段
        String[] includeFields = new String[] {"docId", "title"};
        String[] excludeFields = new String[] {"_type"};
        sourceBuilder.fetchSource(includeFields, excludeFields);

        // 设置返回 profile
        //sourceBuilder.profile(true);

        //将请求体加入到请求中
        searchRequest.source(sourceBuilder);

        // 可选的设置
        //searchRequest.routing("routing");

        //加入聚合
            /*TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company")
                    .field("company.keyword");
            aggregation.subAggregation(AggregationBuilders.avg("average_age")
                    .field("age"));
            sourceBuilder.aggregation(aggregation);*/
        //3、发送请求
        try {
            SearchResponse searchResponse = client.search(searchRequest);
            //4、处理响应

            //处理搜索命中文档结果
            SearchHits hits = searchResponse.getHits();

            long totalHits = hits.getTotalHits();
            float maxScore = hits.getMaxScore();
            System.out.println("totalHits:"+totalHits+" maxScore"+maxScore);
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {

                String logIndex = hit.getIndex();
                String type = hit.getType();
                String id = hit.getId();

                //取_source字段值
                String sourceAsString = hit.getSourceAsString(); //取成json串
                Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
                //从map中取字段值
                Document document=new Document();
                String docId = (String) sourceAsMap.get("docId");
                String title = (String) sourceAsMap.get("title");
                document.setDocId(docId);
//                System.out.println(title);

                logger.info("logIndex:" + index + "  type:" + type + "  id:" + id);
                logger.info(sourceAsString);
                documentsList.add(document);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return documentsList;
    }
    /**
     * 新增，修改文档
     *
     * @param indexName 索引
     * @param type      mapping type
     * @param id        文档id
     */
    public void addData(String indexName, String type, String id, String docId, String title, String content) {
        try {
            // 1、创建索引请求  //索引  // mapping type  //文档id
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("docId", docId);
            map.put("title", title);
            map.put("content", content);
            String jsonStr = JSON.toJSONString(map);
            IndexRequest request = new IndexRequest(indexName, type, id);     //文档id
            // 2、准备文档数据
            // 直接给JSON串
            request.source(jsonStr, XContentType.JSON);
            //4、发送请求
            IndexResponse indexResponse = null;
            try {
                // 同步方式
                indexResponse = client.index(request);
            } catch (ElasticsearchException e) {
                // 捕获，并处理异常
                //判断是否版本冲突、create但文档已存在冲突
                if (e.status() == RestStatus.CONFLICT) {
                    System.out.println("冲突了，请在此写冲突处理逻辑！" + e.getDetailedMessage());
                }
            }
            //5、处理响应
            if (indexResponse != null) {
                String index1 = indexResponse.getIndex();
                String type1 = indexResponse.getType();
                String id1 = indexResponse.getId();
                long version1 = indexResponse.getVersion();
                if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                    System.out.println("新增文档成功!" + index1 + type1 + id1 + version1);
                } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    System.out.println("修改文档成功!");
                }
                // 分片处理信息
                ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
                if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                    System.out.println("分片处理信息.....");
                }
                // 如果有分片副本失败，可以获得失败原因信息
                if (shardInfo.getFailed() > 0) {
                    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                        String reason = failure.reason();
                        System.out.println("副本失败原因：" + reason);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createList(List<Document> puts)  {
        for (Document document : puts) {
            addData("doc", "xhs", document.getDocId(), document.getDocId(), document.getTitle(), document.getContent());
        }
        System.out.println("done");
    }
}