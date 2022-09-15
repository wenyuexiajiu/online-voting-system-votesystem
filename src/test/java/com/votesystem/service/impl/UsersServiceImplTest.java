package com.votesystem.service.impl;

import com.alibaba.druid.filter.AutoLoad;
import com.votesystem.common.Result;
import com.votesystem.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsersServiceImplTest {

    @Autowired
    private UsersService usersService;

    @Value("${filePath.location}")
    private String rootPath;

    @Test
    void isRegistered() {
        Result admin = usersService.isRegistered("admin");
        System.out.println("admin = " + admin);
    }

    @Test
    void sendEmail() {
        String emailCode = usersService.sendEmailCode("2738818582@qq.com");
        System.out.println("emailCode = " + emailCode);
    }

    @Test
    void uploadFile() throws IOException {

        String absolutePath = new File(rootPath).getAbsolutePath();
        String realative = new File(rootPath).getCanonicalPath();
        System.out.println("absolutePath = " + absolutePath);
        String toString = UUID.randomUUID().toString();
        System.out.println("toString = " + toString);
        File file = new File(rootPath, "b78da21d-f173-4a58-823c-87a0e6ec4495wallhaven-8ovg52_3840x2160.png");
        System.out.println("file.exists() = " + file.delete());

    }

    @Test
    void uploadLazy() {
        String absolutePath = new File(rootPath).getAbsolutePath();
        String[] strings = absolutePath.split("/");

        System.out.println("strings = " + strings);
        for (int i = 0; i < strings.length; i++) {
            System.out.println("strings = " + strings[i]);
        }
        String join = String.join("\\\\", strings);
        System.out.println(join);
        String te = "E:\\大三下课程资料\\专周实训\\code\\src\\main\\resources\\static\\headImg\\";
        System.out.println("te = " + te);
    }
}