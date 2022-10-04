package com.hspro.controller;

import com.hspro.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/1
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${images.location}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //获取原始名称
        String originalFilename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        originalFilename = uuid+originalFilename;

        File check_dir = new File(basePath);
        if(!check_dir.exists()){
            check_dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath+originalFilename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(originalFilename);
    }
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len=0;
            byte[] bytes = new byte[1024];
            while((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println("图片加载错误");
        }
    }
}
