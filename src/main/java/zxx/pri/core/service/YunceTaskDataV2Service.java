package zxx.pri.core.service;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.update.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import zxx.pri.core.esconfigs.ElasticSearchProperties;
import zxx.pri.core.mapper.TaskDataV2Mapper;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author : liyan
 * @Description : TODO
 * @date : 2019-06-03 13:48
 **/
@Service
public class YunceTaskDataV2Service {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    private ElasticSearchProperties elasticSearchProperties;
    @Resource
    private TaskDataV2Mapper taskDataV2Mapper;
    @Resource
    private MapInsertUtil mapInsertUtil;
    @Resource
    private EsNestedUtils esNestedUtils;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 更新realId 对应的数据到es
     *
     * @return
     */
    @Async
    public void insertRealData(Long realId) throws Exception {
        List<Map<String, Object>> mapList = taskDataV2Mapper.findDataByidsV3(realId);
        log.warn("realId {} ,数据量条数为 {}", realId, mapList.size());
        if (mapList.size() == 0) {
            return;
        }
        for (Map<String, Object> m : mapList) {
            boolean flag = esNestedUtils.oneAdd(m);
            if (!flag) {
                System.out.print("操作失败");
            }
        }
    }

    public Boolean deleteEsTaskDataById(Long realId) throws Exception {
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        String dataType = elasticSearchProperties.getTypes().get("ES_DATA_TYPE");
        //从数据库中查到es表yunce_task_data的父类所需要的realId
        List<Long> idLst = taskDataV2Mapper.findTaskDataIdByDataIdV2(realId);
        for (Long id : idLst) {
            esNestedUtils.deleteEsTask(id, realId, taskDataIndex, dataType);
        }
        return true;
    }

    public Boolean delDataByIdAndRealId(Map<String, Object> data) throws Exception {
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        String dataType = elasticSearchProperties.getTypes().get("ES_DATA_TYPE");
        // Iterator entrySet 获取key and value
        Iterator<Map.Entry<String, Object>> it = data.entrySet().iterator();
        List<UpdateRequest> updateRequests = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            Object obj = entry.getValue();
            if (StringUtils.isEmpty(obj)) {
                continue;
            }
            String realId = String.valueOf(entry.getKey());
            List ids = JSON.parseArray(obj.toString());
            /*List<DataEntity> dataEntities = esNestedUtils.selectIds(realId);
            List<Long> idss = dataEntities.stream().map(dataEntity -> dataEntity.getId()).collect(Collectors.toList());*/
            for (Object id : ids) {
                //esNestedUtils.getUpdateRequest1(realId, id.toString(), taskDataIndex, dataType);
                UpdateRequest updateRequest = esNestedUtils.getUpdateRequest(realId, id.toString(), taskDataIndex, dataType);
                //if (idss.contains(id)) {
                updateRequests.add(updateRequest);
                //}
            }
        }
        return esNestedUtils.updataReq(updateRequests);
    }

    public Boolean delDataByRealIds(String realIds) throws Exception {
        Boolean flag = true;
        String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        String dataType = elasticSearchProperties.getTypes().get("ES_DATA_TYPE");
        List<String> realIdList = Arrays.asList(realIds.trim().split(","));
        flag = esNestedUtils.delAll(realIdList, taskDataIndex, dataType);
        return flag;
    }


    /**
     * @param : [userId, id]
     * @return : java.lang.Boolean
     * @description: 修改es中的read，0代表未读，1代表已读，将0修改为1
     * @author : liyan
     * @date : 2019/4/24
     */
    public Boolean updataEsTaskRead(Long userId, Long id) throws Exception {
        /*String taskDataIndex = elasticSearchProperties.getIndexs().get("ES_TASK_DATA_V2_INDEX");
        String dataType = elasticSearchProperties.getTypes().get("ES_DATA_TYPE");
        //从数据库中查到es表yunce_task_data的父类所需要的realId
        Long realId = taskDataV2Mapper.findDataByRealIdAndDataIDV2(userId,id);
         return esNestedUtils.updataTask(id,realId,taskDataIndex,dataType);*/
        String key = "read" + ":" + userId;
        stringRedisTemplate.opsForSet().add(key, id + "");
        return true;
    }

}
