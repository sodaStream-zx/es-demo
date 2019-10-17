package zxx.pri.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import zxx.pri.core.mapper.DataInfoMapper;
import zxx.pri.core.service.YunceTaskDataV2Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Twilight
 * @desc
 * @createTime 2019-05-01-00:03
 */
@RestController
public class IndexController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    private DataInfoMapper dataInfoMapper;
    @Autowired
    private YunceTaskDataV2Service yunceTaskDataV2Service;

    @PostMapping("/newTaskDataToES")
    public String insertewTaskDataToEsV2(Long realId, Integer type) throws Exception {
        if (!StringUtils.isEmpty(realId)) {
            log.warn("-------->>  插入单个realid {}", realId);
            Long st = System.currentTimeMillis();
            yunceTaskDataV2Service.insertRealData(realId, type);
            log.warn("-------->>  插入realid {} ，所有数据 用时：{}", realId, (System.currentTimeMillis() - st));
        } else {
            log.warn("-------->> 遍历所有realid");
            List<Long> longs = dataInfoMapper.listOfReaIds();
            longs.stream().forEach(aLong -> {
                try {
                    Long st = System.currentTimeMillis();
                    yunceTaskDataV2Service.insertRealData(aLong, type);
                    log.warn(" -------->> 插入realid {} ，所有数据 用时：{}", aLong, (System.currentTimeMillis() - st) / 1000);
                } catch (Exception e) {
                    log.error(" -------->>  异常 {}", e);
                }
            });
        }
        return "添加成功";
    }
}
