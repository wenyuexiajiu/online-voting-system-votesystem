package com.votesystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author DengJin
 * @ClassName MyWebMvcConfig
 * @date 2022-07-13 23:33
 * @Description:
 * @Version:1.0
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Value("${filePath.location}")
    private String rootPath;

    /**
     * 映射头像文件地址，能够实现上传头像后，立马显示到页面上并不会出现缓存现象
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = new File(rootPath).getAbsolutePath();
        registry.addResourceHandler("/headImg/**").addResourceLocations("file:" + absolutePath + "\\");
    }
}
