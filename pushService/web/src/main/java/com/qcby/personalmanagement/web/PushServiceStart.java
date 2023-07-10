package com.qcby.personalmanagement.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author cong.zhen
 * @date 2023/04/03
 */
@SpringBootApplication
@MapperScan(basePackages = "com.qcby.personalmanagement.base.mapper")
@ComponentScan(basePackages = "com.qcby.personalmanagement")
public class PushServiceStart {
    public static void main(String[] args) {
        SpringApplication.run(PushServiceStart.class, args);
    }
}
