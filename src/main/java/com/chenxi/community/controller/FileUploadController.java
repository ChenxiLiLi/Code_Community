package com.chenxi.community.controller;

import com.chenxi.community.dto.FileDTO;
import com.chenxi.community.provider.AliyunOssProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: Mr.Chen
 * @Description: 文件上传控制器
 * @Date:Created in 20:43 2020/3/29
 */
@Controller
@Slf4j
public class FileUploadController {

    @Autowired
    private AliyunOssProvider aliyunOssProvider;

    @ResponseBody
    @RequestMapping(value = "/file/upload")
    public FileDTO upload(HttpServletRequest request) {
        //获取文件
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        String fileName = null;
        try {
            fileName = aliyunOssProvider.uploadFile(file, "images");
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(fileName);
            return fileDTO;
        } catch (IOException e) {
            log.error("upload error", e);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
            return fileDTO;
        }
    }
}
