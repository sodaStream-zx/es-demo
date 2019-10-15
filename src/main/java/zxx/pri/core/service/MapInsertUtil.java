package zxx.pri.core.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : liyan
 * @Description : TODO
 * @date : 2019-06-04 8:59
 **/
@Component
public class MapInsertUtil {

    @Resource
    private EsNestedUtils esUtil;

    public void insertMap(List<Map<String, Object>> newList) {
        //map的键对应数据id，值对应任务id，是否已读等
        Map<Long, List<Map<String, Object>>> longListMap = new HashMap<>();
        List<Map<String, Object>> mapList;
        for (Map<String, Object> map : newList) {
            Map<String, Object> map1 = new HashMap<>();
            if (longListMap.keySet().contains(map.get("id"))) {
                mapList = longListMap.get(map.get("id"));
            } else {
                mapList = new ArrayList<>();
            }
            map1.put("id", map.get("realId"));
            map1.put("searchKey", "");
            mapList.add(map1);
            longListMap.put((Long) map.get("id"), mapList);
        }

        List<List<Map<String, Object>>> listList = Lists.partition(newList, 300);
        for (List<Map<String, Object>> mapList1 : listList) {
            esUtil.batchAdd(mapList1, longListMap);
        }

    }
}
