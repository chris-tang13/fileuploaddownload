package com.chris.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class DownloadController {
    @RequestMapping("download")
    public void path(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取图片的路径信息
        String imagesPath = request.getServletContext().getRealPath("images/测试.jpg");
        File file=new File(imagesPath);
        //文件下载必须加上这个响应头
        response.addHeader("Content-Disposition", "attachment; filename=" +file.getName());
        //防止文件名称为中文，可以使用下面的代码windows中文编码格式是gbk，先按gbk拿到字节数组，再将字节数组按浏览器编码格式ISO-8859-1转换成字符
        //response.addHeader("Content-Disposition", "attachment; filename=" + new String(file.getName().getBytes("gbk"),"ISO-8859-1"));
        FileInputStream fileInputStream = new FileInputStream(file);

       //将文件按字节读出来封装到一个字节数组里面去
        byte[] readBuffer = new byte[5];
        int len=0;
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        while ((len=fileInputStream.read(readBuffer))!=-1){
            byteArrayOutputStream.write(readBuffer);
        }
        byte[] fileBytes = byteArrayOutputStream.toByteArray();

        //将文件字节数组写出给浏览器
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(fileBytes);

    }

}
