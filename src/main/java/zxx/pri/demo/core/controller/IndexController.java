package zxx.pri.demo.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Twilight
 * @desc
 * @createTime 2019-05-01-00:03
 */
@RestController
public class IndexController {
    @GetMapping(value = "/index")
    public String myTest() {
        return "index";
    }
}
