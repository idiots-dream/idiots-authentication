package com.idiots.authentication;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author wjj70
 * 权限认证系统
 */
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("com.idiots.authentication.mapper")
public class IdiotsAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdiotsAuthenticationApplication.class, args);
    }

}
