package com.chris.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;


@RestController
public class TestController {

    //服务器传给浏览器
    @RequestMapping("test")
    public void test(HttpServletResponse response, HttpServletRequest request) throws IOException {

        //必须设置contenttype为image/jpeg
        response.setContentType("image/jpeg");


        File file = new File("D:\\Downloads\\aa.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        //一次读取整个文件的字节数组
//        byte[] buffer=new byte[fileInputStream.available()];
//        fileInputStream.read(buffer);
//        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(buffer);

        //多次读取将文件封装到一个字节数组中
        int len;
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        byte[] bytes=new byte[5];
        while ((len = fileInputStream.read(bytes)) != -1){
            byteArrayOutputStream.write(bytes);
        }
        byte[] bytes1 = byteArrayOutputStream.toByteArray();

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes1);

    }

    //浏览器传给服务器
    @PostMapping("upload")
    public String upload(MultipartFile fileUpload) throws IOException {
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        //指定本地文件夹存储图片
        String filePath = "D:/";
        fileUpload.transferTo(new File(filePath+fileName));
        return "上传成功";

    }
}
