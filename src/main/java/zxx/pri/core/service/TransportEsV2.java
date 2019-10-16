package zxx.pri.core.service;

import com.alibaba.fastjson.JSON;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import zxx.pri.core.config.esconfigs.ElasticSearchProperties;
import zxx.pri.core.entity.DataPojo;
import zxx.pri.core.mapper.DataInfoMapper;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xiaochanggui
 * @date 14:56
 */
@Service
public class TransportEsV2 {

    private static SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    private ElasticSearchProperties elasticSearchProperties;
    @Resource
    private DataInfoMapper dataInfoMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private JestHttpClient jestHttpClient;

    public String searchDataList(int page, int pageSize, Long userId, String content, String appraise,
                                 Long dateType, String type, Long realId) throws Exception {
        //父类
        long start = System.currentTimeMillis();
        //子类
        String esDataType = elasticSearchProperties.getTypes().get("ES_DATA_TYPE");
        //索引
        String esTaskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        String word = "";
        String name = "";
        if (StringUtils.isNotEmpty(content) && !"全部".equals(content)) {
            String[] contents = content.split(" ");
            word = splitWords(contents[0]);
            if (contents.length > 1) {
                name = contents[1];
            }
        }
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //开始时间
        String begainTime = "";
        Integer newCount = 0;
        //结束时间
        String endTime = YMDHMS.format(new Date());
        List<Long> realIdList = new ArrayList<>();

        String[] wordKeys = null;
        if (StringUtils.isNotEmpty(word)) {
            wordKeys = word.split(" ");
        }
        List<Map<String, Object>> keyAndRealds = dataInfoMapper.getKeyAndRealId(userId);
        if (realId == 0) {
            for (Map<String, Object> map : keyAndRealds) {
                if (StringUtils.isNotEmpty(content) && content.equals(map.get("searchKeys"))) {
                    realId = Long.valueOf(map.get("realId") + "");
                    realIdList = new ArrayList<>();
                    realIdList.add(realId);
                    break;
                }
                if (StringUtils.isEmpty(content)) {
                    realIdList.add(Long.valueOf(map.get("realId") + ""));
                }
                if (StringUtils.isNotEmpty(content) && !content.equals(map.get("searchKeys"))) {
                    realIdList.add(Long.valueOf(map.get("realId") + ""));
                }
            }
            if (realIdList.size() > 0) {
                for (Long id : realIdList) {
                    String key = userId + "-search-newData-" + id;
                    if (redisTemplate.hasKey(key)) {
                        Object obj = redisTemplate.opsForValue().get(key);
                        if (obj != null && !"".equals(obj)) {
                            newCount += Integer.parseInt(obj + "");
                        }
                        redisTemplate.delete(key);
                    }
                }
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("status", 1);
                map.put("message", "暂无数据");
                map.put("data", new ArrayList<>());
                map.put("newCount", 0);
                return JSON.toJSONString(map);
            }
        } else {
            realIdList.add(realId);
        }
        queryBuilder.must(QueryBuilders.nestedQuery("searchKeys",
                QueryBuilders.boolQuery().must(
                        QueryBuilders.termsQuery("searchKeys.id", realIdList)),
                ScoreMode.Total));
        //判断搜索内容
        if (StringUtils.isNotEmpty(content) && realId == 0) {
            //matchPhraseQuery  模糊查询的精确匹配
            if (StringUtils.isNotEmpty(name)) {
                queryBuilder.must(QueryBuilders.matchPhraseQuery("content", name));
            }
            if (!org.springframework.util.StringUtils.isEmpty(wordKeys)) {
                for (String words : wordKeys) {
                    queryBuilder.must(QueryBuilders.matchPhraseQuery("content", words.trim()));
                }
            }
        }
        //判断媒体类型
        if (StringUtils.isNotEmpty(type) && !"全部".equals(type)) {
            // termQuery  精确查询
            queryBuilder.must(QueryBuilders.termQuery("type", type));
        }
        //判断情感倾向性
        if (StringUtils.isNotEmpty(appraise) && !"全部".equals(appraise)) {
            queryBuilder.must(QueryBuilders.termQuery("appraise", appraise));
        }
        if (dateType == 0) {
            //一个月
            begainTime = DateUtils.getlastMonthTime(YMDHMS);
            queryBuilder.filter(QueryBuilders.rangeQuery("publishDate").gt(begainTime).lt(endTime));
        } else if (dateType == 1) {
            //三个月
            begainTime = DateUtils.getlastThreeTime(YMDHMS);
            queryBuilder.filter(QueryBuilders.rangeQuery("publishDate").gt(begainTime).lt(endTime));
        } else if (dateType == 2) {
            //一年
            begainTime = DateUtils.getLastYear(YMDHMS);
            queryBuilder.filter(QueryBuilders.rangeQuery("publishDate").gt(begainTime).lt(endTime));
        }

        //从第几条数据开始
        int pageBegainNum = (page - 1) * pageSize;
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        ssb.query(queryBuilder).sort("publishDate", SortOrder.DESC).size(pageSize).from(pageBegainNum);
        Search search = new Search.Builder(ssb.toString())
                .addIndex(esTaskDataIndex)
                .addType(esDataType)
                .build();
        SearchResult result = jestHttpClient.execute(search);
        List<SearchResult.Hit<DataPojo, Void>> hitList = result.getHits(DataPojo.class);
        List<DataPojo> listBean = new ArrayList<>();
        hitList.stream().forEach(dataInfoEntityVoidHit -> listBean.add(dataInfoEntityVoidHit.source));
        if (ObjectUtils.isEmpty(listBean) || 0 == listBean.size()) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("message", "暂无数据");
            map.put("data", listBean);
            map.put("newCount", 0);
            return JSON.toJSONString(map);
        }
        listBean.stream().forEach(dataPojo -> dataPojo.setContent(""));
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "查询成功");
        map.put("data", listBean);
        map.put("newCount", newCount);
        return JSON.toJSONString(map);
    }

    private String splitWords(String word) {
        List<Term> list = HanLP.segment(word);
        StringBuffer sb = new StringBuffer();
        for (Term term : list) {
            if (org.apache.commons.lang3.StringUtils.isEmpty(term.word.trim())) {
                continue;
            }
            sb.append(" ").append(term.word.trim());
        }

        if (sb.length() > 1) {
            return sb.substring(1);
        }
        return "";
    }
}
