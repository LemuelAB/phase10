package com.lagou.eduauthorityboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient // 注册到中心的客户端
@MapperScan("com.lagou.eduauthorityboot.mapper")
public class EduAuthorityBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduAuthorityBootApplication.class, args);
    }

}
