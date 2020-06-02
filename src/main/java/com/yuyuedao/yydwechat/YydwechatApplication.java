package com.yuyuedao.yydwechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"com.yuyuedao.yydwechat.mapper.generator","com.yuyuedao.yydwechat.mapper"})
@ServletComponentScan(basePackages = "com.yuyuedao.yydwechat.servlet")
@SpringBootConfiguration
@EnableTransactionManagement
public class YydwechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(YydwechatApplication.class, args);
    }

}
