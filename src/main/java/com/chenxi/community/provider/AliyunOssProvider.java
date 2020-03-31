package com.chenxi.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 17:08 2020/3/30
 * 用户登录名称 chenxilili@1779281537619261.onaliyun.com
 * AccessKey ID LTAI4FtSVcWAaji1Qj1Dg91G
 * AccessKeySecret h2d79kGyNRzmpo9gZdZO56rEs2v9QG
 */
@Service
public class AliyunOssProvider {

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;
    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.endpoint}")
    private String endPoint;
    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;
    @Value("${aliyun.oss.access-uri}")
    private String accessUrl;

    OSS client = null;

    public String uploadFile(MultipartFile multipartFile, String remotePath) throws IOException {
        //将multipartFile转换成相应的IO流
        InputStream fileContent = multipartFile.getInputStream();
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        System.out.println(fileName);
        //修改文件名
        fileName = "tcx_" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        //初始化OSSClient
        client = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        //定义二级目录
        String remoteFilePath = remotePath.replaceAll("\\\\", "/") + "/";
        ObjectMetadata objectMetadata = new ObjectMetadata();
        //设置必要的属性
        objectMetadata.setContentLength(fileContent.available());
        objectMetadata.setContentEncoding("utf-8");
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(fileName.substring(fileName.lastIndexOf(".")));
        objectMetadata.setContentDisposition("inline;filename=" + fileName);
        //上传文件
        System.out.print(remoteFilePath);
        client.putObject(bucketName, remoteFilePath+fileName , fileContent, objectMetadata);
        client.shutdown();
        fileContent.close();
        return accessUrl + "/" + remoteFilePath + fileName;
    }

}
