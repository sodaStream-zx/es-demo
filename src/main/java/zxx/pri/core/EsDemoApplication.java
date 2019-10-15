package zxx.pri.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Twilight
 * @desc
 * @createTime 2019-04-30-20:15
 */
@SpringBootApplication
@EnableAsync
public class EsDemoApplication {
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(EsDemoApplication.class, args);
    }
}
