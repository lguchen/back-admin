package com.gu.backadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.gu.backadmin.mapper")
public class BackAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackAdminApplication.class, args);
    }

}
