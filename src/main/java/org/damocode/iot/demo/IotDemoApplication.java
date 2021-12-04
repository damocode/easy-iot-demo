package org.damocode.iot.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: 启动类
 * @Author: zzg
 * @Date: 2021/10/7 17:28
 * @Version: 1.0.0
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass=true)
@MapperScan("org.damocode.iot.demo.mapper.**")
public class IotDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotDemoApplication.class, args);
    }

}
