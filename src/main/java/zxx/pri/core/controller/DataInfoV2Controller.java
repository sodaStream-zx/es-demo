package zxx.pri.core.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zxx.pri.core.entity.ResData;
import zxx.pri.core.service.TransportEsV2;

/**
 * 数据详情 控制层
 *
 * @program: yunce-cloud
 * @description:
 * @author: TangChao
 * @create: 2019-01-18 15:23
 **/

@RestController
@RequestMapping(value = "/dataInfo")
public class DataInfoV2Controller {

    @Autowired
    private TransportEsV2 transportEs;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 数据列表与搜索
     *
     * @return java.util.Map
     * @Author TangChao
     * @Date 15:31 2019/1/18 0018
     * @Param [imie, search]
     **/
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String searchDataList(int page, int pageSize, Long userId, String content, String appraise, Long dateType, String type, Long realId) throws Exception {
        if (ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(pageSize) || ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(dateType)) {
            return JSON.toJSONString(new ResData<>(0, "参数错误", null));
        }
        if (realId == null) {
            realId = 0L;
        }
        return transportEs.searchDataList(page, pageSize, userId, content, appraise, dateType, type, realId);
    }

}
