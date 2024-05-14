package com.ssafy.hw.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.ssafy.hw.model.dao")
@Configuration
public class MybatisConfig {

}
