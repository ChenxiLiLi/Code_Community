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
        //获取上传文件请求
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //从前端的元素获取文件对象
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        String fileName = null;
        FileDTO fileDTO = new FileDTO();
        //文件不为空
        if (file != null) {
            try {
                //上传文件
                fileName = aliyunOssProvider.uploadFile(file, "images");
                //上传成功
                fileDTO.setSuccess(1);
                fileDTO.setUrl(fileName);
            } catch (IOException e) {
                //上传文件出现异常
                log.error("upload error", e);
                fileDTO.setSuccess(0);
                fileDTO.setMessage("上传失败");
            }
        } else {    //文件为空
            System.out.println("选择的文件为空，请重新尝试上传");
        }
        return fileDTO;
    }
}
