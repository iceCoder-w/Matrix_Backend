package com.wang.fileservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * @author 王冰炜
 * @create 2021-05-18 2:33
 */

//@Configuration
public class TomcatConfig {

//    @Value("${spring.server.MaxFileSize}")
//    private String MaxFileSize;
//    @Value("${spring.server.MaxRequestSize}")
//    private String MaxRequestSize;
//
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //  单个数据大小
//        factory.setMaxFileSize(DataSize.of(Long.parseLong(MaxFileSize), DataUnit.MEGABYTES)); // KB,MB
//        /// 总上传数据大小
//        factory.setMaxRequestSize(DataSize.of(Long.parseLong(MaxRequestSize), DataUnit.MEGABYTES));
//        return factory.createMultipartConfig();
//    }
}