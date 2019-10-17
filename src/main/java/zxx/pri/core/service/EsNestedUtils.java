package zxx.pri.core.service;

import io.searchbox.client.JestResult;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.*;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zxx.pri.core.config.esconfigs.ElasticSearchProperties;
import zxx.pri.core.entity.DataEntity;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * @Author : liyan
 * @Description : TODO
 * @date : 2019-05-31 11:08
 **/
@Component
public class EsNestedUtils {

    private static final Logger log = LoggerFactory.getLogger(EsNestedUtils.class);

    @Autowired
    private Client transportClient;
    @Resource
    private ElasticSearchProperties elasticSearchProperties;
    @Autowired
    private JestHttpClient jestHttpClient;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Boolean insertDataAndTask(Map<String, Object> map, String taskDataIndex, String taskDataType) throws IOException {
        DocumentResult result = jestHttpClient.
                execute(new Index.Builder(map).
                        index(taskDataIndex).
                        type(taskDataType).id(map.get("id") + "").refresh(true).build());
        return result.isSucceeded();
    }

    public Boolean delete(Long id) throws Exception {
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        JestResult result = jestHttpClient.execute(new Delete.Builder(id + "").index(taskDataIndex).type("data").build());
        return result.isSucceeded();
    }

    public Boolean updata(Long id, String name) {
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(taskDataIndex);
        updateRequest.type("data");
        updateRequest.id(id + "");
        try {
            updateRequest.doc(XContentFactory.jsonBuilder().startObject().field(name, "唐山新闻网").endObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
        transportClient.update(updateRequest);
        return true;
    }

    public Boolean deleteEsTask(Long id, Long realId, String taskDataIndex, String taskDataType) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(taskDataIndex);
        updateRequest.type(taskDataType);
        updateRequest.id(id + "");
        updateRequest.script(new Script("ctx._source.searchKeys.removeIf(it -> it.id == " + realId + ")"));
        transportClient.update(updateRequest);
        return true;
    }


    public Boolean updataReq(List<UpdateRequest> updateRequests) throws Exception {
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        Boolean flag = true;
        for (UpdateRequest updateRequest : updateRequests) {
            bulkRequest.add(updateRequest);
        }
        if (bulkRequest.numberOfActions() == 0) {
            return flag;
        }
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = bulkRequest.execute().actionGet();
        } catch (Exception e) {
            e.getMessage();
        }
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.out.println(bulkResponse.buildFailureMessage());
            flag = false;
        }
        return flag;
    }

    public Boolean delAll(List<String> realIdList, String taskDataIndex, String taskDataType) throws Exception {
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        Boolean flag = true;
        for (String realId : realIdList) {
            List<DataEntity> ids = selectIds(realId);
            for (DataEntity dataEntity : ids) {
                UpdateRequest updateRequest = this.getUpdateRequest(realId, dataEntity.getId().toString(), taskDataIndex, taskDataType);
                bulkRequest.add(updateRequest);
            }
        }
        if (bulkRequest.numberOfActions() == 0) {
            return flag;
        }
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.out.println(bulkResponse.buildFailureMessage());
            flag = false;
        }
        return flag;
    }

    public UpdateRequest getUpdateRequest(String realId, String id, String taskDataIndex, String taskDataType) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(taskDataIndex);
        updateRequest.type(taskDataType);
        updateRequest.id(id + "");
        updateRequest.script(new Script("ctx._source.searchKeys.removeIf(it -> it.id == " + realId + ")"));
        return updateRequest;
    }

    public void getUpdateRequest1(String id, String realId, String taskDataIndex, String taskDataType) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(taskDataIndex);
        updateRequest.type(taskDataType);
        updateRequest.id(id + "");
        updateRequest.script(new Script("ctx._source.searchKeys.removeIf(it -> it.id == " + realId + ")"));
        try {
            transportClient.update(updateRequest);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public List<DataEntity> selectIds(String realId) throws Exception {
        //子类
        String esDataType = elasticSearchProperties.getTypes().get("ES_DATA_TYPE");
        //索引
        String esTaskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.nestedQuery("searchKeys",
                QueryBuilders.boolQuery().must(
                        QueryBuilders.termQuery("searchKeys.id", realId)),
                ScoreMode.Total));
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        ssb.size(10000);
        Search search = new Search.Builder(ssb.toString())
                .addIndex(esTaskDataIndex)
                .addType(esDataType)
                .build();
        SearchResult result = jestHttpClient.execute(search);
        List<SearchResult.Hit<DataEntity, Void>> hitList = result.getHits(DataEntity.class);
        List<DataEntity> listBean = new ArrayList<>();
        hitList.stream().forEach(dataInfoEntityVoidHit -> listBean.add(dataInfoEntityVoidHit.source));
        return listBean;
    }

    public String batchAdd(List<Map<String, Object>> mapList, Map<Long, List<Map<String, Object>>> longListMap) {
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        String dataType = elasticSearchProperties.getTypes().get("ES_DATA_TYPE");
        List<Index> indexList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            map.put("warn", (Boolean) map.get("warn") ? 1 : 0);
            map.remove("realId");
            map.put("searchKeys", longListMap.get(map.get("id")));
            io.searchbox.core.Index index = new io.searchbox.core.Index.Builder(map)
                    .index(taskDataIndex)
                    .type(dataType)
                    .id(map.get("id") + "").refresh(true).build();
            indexList.add(index);
        }
        JestHttpClient tClient = jestHttpClient;
        Bulk bulk = new Bulk.Builder().defaultIndex(taskDataIndex)
                .defaultType(dataType).addAction(indexList).build();
        try {
            BulkResult execute = tClient.execute(bulk);
            logger.info("批量同步es:" + execute.isSucceeded());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean oneAddByJest(Map<String, Object> targetMap) throws Exception {
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V3_INDEX");
        String dataType = elasticSearchProperties.getTypes().get("ES_DATA_TYPE2");

        SearchRequestBuilder sbuilder = transportClient.prepareSearch(taskDataIndex).setTypes(dataType);
        SearchResponse actionGet = sbuilder.setQuery(QueryBuilders.idsQuery().addIds(targetMap.get("id") + "")).execute().actionGet();
        if (actionGet.getHits().hits().length > 0) {
            //存在
            SearchHit hit = actionGet.getHits().getHits()[0];
            Object searchKeys = hit.getSource().get("searchKeys");
            Map<String, Object> d = geneMap(targetMap);
            if (searchKeys != null) {
                Set<Map<String, Object>> set = new HashSet<>();
                List<Map<String, Object>> keys = new ArrayList<>();
                try {
                    keys = (List) searchKeys;
                    set = new HashSet<>(keys);
                } catch (Exception e) {
                    logger.warn("dataId:{}", hit.getSource().get("id"));
                    if (e instanceof ClassCastException) {
                        Map<String, Object> dd = (Map<String, Object>) searchKeys;
                        set.add(dd);
                    }
                }
                if (set.size() > 0) {
                    if (!compareListMap(set, d)) {
                        set.add(d);
                    } else {
                        if (keys.size() == set.size()) {
                            return true;
                        }
                    }
                }
                targetMap.put("searchKeys", new ArrayList<>(set));
            } else {
                List<Map<String, Object>> list = new ArrayList<>();
                list.add(d);
                targetMap.put("searchKeys", list);
            }
        } else {
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(geneMap(targetMap));
            targetMap.put("searchKeys", list);
        }
        targetMap.put("warn", (Boolean) targetMap.get("warn") ? 1 : 0);
        targetMap.remove("realId");
        targetMap.remove("keyWords");
        JestHttpClient tClient = jestHttpClient;
        try {
            Index index = new Index.Builder(targetMap)
                    .index(taskDataIndex)
                    .type(dataType)
                    .id(targetMap.get("id").toString())
                    .refresh(true)
                    .build();
            DocumentResult result = jestHttpClient.execute(index);
            if (!result.isSucceeded()) {
                logger.error("插入失败:" + targetMap);
                logger.error("错误信息:" + result.getErrorMessage());
            }
            return result.isSucceeded();
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                Index index = new Index.Builder(targetMap)
                        .index(taskDataIndex)
                        .type(dataType)
                        .id(targetMap.get("id").toString())
                        .refresh(true)
                        .build();
                DocumentResult result = jestHttpClient.execute(index);
            }
            throw new Exception(e);
        }
    }

    public boolean oneAdd(Map<String, Object> targetMap) throws Exception {
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V3_INDEX");
        String dataType = elasticSearchProperties.getTypes().get("ES_DATA_TYPE2");

        SearchRequestBuilder sbuilder = transportClient.prepareSearch(taskDataIndex).setTypes(dataType);
        SearchResponse actionGet = sbuilder.setQuery(QueryBuilders.idsQuery().addIds(targetMap.get("id") + "")).execute().actionGet();
        if (actionGet.getHits().hits().length > 0) {
            //存在
            SearchHit hit = actionGet.getHits().getHits()[0];
            Object searchKeys = hit.getSource().get("searchKeys");
            Map<String, Object> d = geneMap(targetMap);
            if (searchKeys != null) {
                Set<Map<String, Object>> set = new HashSet<>();
                List<Map<String, Object>> keys = new ArrayList<>();
                try {
                    keys = (List) searchKeys;
                    set = new HashSet<>(keys);
                } catch (Exception e) {
                    logger.warn("dataId:{}", hit.getSource().get("id"));
                    if (e instanceof ClassCastException) {
                        Map<String, Object> dd = (Map<String, Object>) searchKeys;
                        set.add(dd);
                    }
                }
                if (set.size() > 0) {
                    if (!compareListMap(set, d)) {
                        set.add(d);
                    } else {
                        if (keys.size() == set.size()) {
                            return true;
                        }
                    }
                }
                targetMap.put("searchKeys", new ArrayList<>(set));
            } else {
                List<Map<String, Object>> list = new ArrayList<>();
                list.add(d);
                targetMap.put("searchKeys", list);
            }
        } else {
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(geneMap(targetMap));
            targetMap.put("searchKeys", list);
        }
        targetMap.put("warn", (Boolean) targetMap.get("warn") ? 1 : 0);
        targetMap.remove("realId");
        targetMap.remove("keyWords");
        JestHttpClient tClient = jestHttpClient;
        try {
            DocumentResult result = tClient.execute(new Index.Builder(targetMap)
                    .index(taskDataIndex)
                    .type(dataType)
                    .id(targetMap.get("id") + "").refresh(true).build());

            if (!result.isSucceeded()) {
                logger.error("插入失败:" + targetMap);
                logger.error("错误信息:" + result.getErrorMessage());
            }
            return result.isSucceeded();
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                tClient.execute(new Index.Builder(targetMap)
                        .index(taskDataIndex)
                        .type(dataType)
                        .id(targetMap.get("id") + "").refresh(true).build());
            }
            throw new Exception(e);
        }
    }

    private boolean compareListMap(Set<Map<String, Object>> keys, Map<String, Object> map) {
        for (Map<String, Object> key : keys) {
            if (key.get("id").toString().equals(map.get("id").toString())) {
                return true;
            }
        }

        return false;
    }

    private Map<String, Object> geneMap(Map<String, Object> map) {

        Map<String, Object> m = new HashMap<>();

        m.put("id", map.get("realId"));
        m.put("searchKey", map.get("keyWords") == null ? "" : map.get("keyWords"));
        m.put("read", 0);

        return m;
    }

    public void select(Long taskId) throws Exception {
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        JestHttpClient tClient = jestHttpClient;
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.nestedQuery("searchKeys",
                QueryBuilders.boolQuery().must(QueryBuilders.termQuery("searchKeys.id", taskId)), ScoreMode.Total));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(taskDataIndex)
                .addType("data")
                .build();

        SearchResult result = tClient.execute(search);
        System.out.print("total:" + result.getTotal());
        System.out.print("nested:::" + result.getJsonString());

        result.getJsonObject().get("hits");
    }

    public void selectList(List<Long> taskIdList) throws Exception {
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        JestHttpClient tClient = jestHttpClient;
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.nestedQuery("searchKeys",
                QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("searchKeys.id", taskIdList)), ScoreMode.Total));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(taskDataIndex)
                .addType("data")
                .build();

        SearchResult result = tClient.execute(search);
        System.out.print("total:" + result.getTotal());

        System.out.print("nested:::" + result.getJsonString());

        result.getJsonObject().get("hits");
    }

    public String getData(List<Long> taskIdList) throws Exception {
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        JestHttpClient tClient = jestHttpClient;
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
       /* for(Long ll:taskIdList){
            queryBuilder.must(QueryBuilders.termQuery("id",ll));
        }*/
        queryBuilder.must(QueryBuilders.termsQuery("id", taskIdList));
        queryBuilder.must(QueryBuilders.termQuery("warn", 0));
        queryBuilder.must(QueryBuilders.termQuery("content", "教育"));
        //queryBuilder.must(QueryBuilders.termsQuery("warn",1));
        //queryBuilder.must(QueryBuilders.termsQuery("content",taskIdList));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0).size(10);
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(taskDataIndex)
                .addType("data")
                .build();
        SearchResult result = tClient.execute(search);
        System.out.print("total:" + result.getTotal());
        /*System.out.print("nested:::"+result.getJsonString());*/
        result.getJsonObject().get("hits");
        return "返回成功";
    }

    public String getData2(List<Long> taskIdList) throws Exception {
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termsQuery("id", taskIdList));
        SearchResponse searchResponse = transportClient.prepareSearch(taskDataIndex)
                .setTypes("data")
                .setQuery(queryBuilder) //查询所有
                //.setQuery(QueryBuilders.matchQuery("name", "tom").operator(Operator.AND)) //根据tom分词查询name,默认or
                //.setQuery(QueryBuilders.multiMatchQuery("tom", "name", "age")) //指定查询的字段
                //.setQuery(QueryBuilders.queryString("name:to* AND age:[0 TO 19]")) //根据条件查询,支持通配符大于等于0小于等于19
                //.setQuery(QueryBuilders.termQuery("name", "tom"))//查询时不分词
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setFrom(0).setSize(10)//分页
                //.addSort("age", SortOrder.DESC)//排序
                .get();

        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits();
        System.out.println(total);
        SearchHit[] searchHits = hits.hits();
        for (SearchHit s : searchHits) {
            //System.out.println(s.getSourceAsString());
        }
        return "成功";
    }
}
