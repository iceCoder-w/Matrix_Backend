package com.wang.fileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 王冰炜
 * @create 2021-05-15 16:52
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.wang"})
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
