package com.chenxi.community.cache;

import com.chenxi.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 22:58 2020/3/25
 */
public class TagCache {
    public static List<TagDTO> getAllTags() {
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO frontEnd = new TagDTO();
        TagDTO rearEnd = new TagDTO();
        TagDTO dataBase = new TagDTO();
        TagDTO operationMaintenance = new TagDTO();
        TagDTO tool = new TagDTO();

        frontEnd.setCategoryName("前端");
        frontEnd.setTags(Arrays.asList("javascript" , "vue.js" , "css" , "html" , "html5" , "node.js" , "react.js" , "jquery" , "css3" , "es6" , "typescript" , "chrome" , "npm" , "bootstrap" , "postman" , "firefox" , "dreamweaver"));
        tagDTOS.add(frontEnd);

        rearEnd.setCategoryName("后端");
        rearEnd.setTags(Arrays.asList("php" , "java" , "node.js" , "python" , "c++" , "c" , "golang" , "spring" , "django" , "springboot" , "flask" , "c#" , "swoole" , "ruby" , "asp.net" , "ruby-on-rails" , "scala" , "rust" , "lavarel"));
        tagDTOS.add(rearEnd);

        dataBase.setCategoryName("数据库");
        dataBase.setTags(Arrays.asList("mysql " , "redis " , "mongodb " , "sql " , "json " , "elasticsearch " , "nosql " , "memcached " , "postgresql " , "sqlite " , "mariadb"));
        tagDTOS.add(dataBase);

        operationMaintenance.setCategoryName("运维");
        operationMaintenance.setTags(Arrays.asList("linux " , "nginx " , "docker " , "apache " , "centos " , "ubuntu " , "ssh " , "vagrant " , "jenkins " , "devops " , "debian"));
        tagDTOS.add(operationMaintenance);

        tool.setCategoryName("工具");
        tool.setTags(Arrays.asList("git " , "github " , "macos " , "visual-studio-code " , "windows " , "vim " , "intellij-idea " , "eclipse " , "typora " , "svn " , "webstorm"));
        tagDTOS.add(dataBase);

        return tagDTOS;
    }

    public static String filterInvalid(String tags) {
        //获取标签数组
        String[] split = StringUtils.split(tags, ",");
        //获取标签种类+标签集
        List<TagDTO> tagDTOS = getAllTags();
        //将二维数组循环出来，获取所有的标签集合
        List<String> tagsList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        //一一匹配，验证传进来的标签是否存在，并返回一个出错的字符串集合
        String invalid = Arrays.stream(split).filter(t -> !tagsList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
