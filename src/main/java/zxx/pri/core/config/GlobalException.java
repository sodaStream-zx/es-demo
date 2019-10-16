package zxx.pri.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import zxx.pri.core.entity.ResData;

import java.util.HashMap;

/**
 * @author zxx
 * @desc 全局异常处理
 * @createTime 2019-05-31-上午 10:51
 */
@ControllerAdvice
public class GlobalException {
    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("causedBy", e.getCause());
        stringObjectHashMap.put("message", e.getMessage());
        //打印错误
        log.error("看见没有??真的是异常 {}", e);
        return new ResData<HashMap>(0, "系统繁忙,请稍后重试", stringObjectHashMap);
    }
}
