package com.chenxi.community.provider;

import com.alibaba.fastjson.JSON;
import com.chenxi.community.dto.AccessTokenDTO;
import com.chenxi.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Mr.Chen
 * @Description: Github登录，提供访问令牌对象
 * @Date:Created in 21:47 2020/3/2
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id=" + accessTokenDTO.getClientId() +"&client_secret=" + accessTokenDTO.getClientSecret() + "&code=" + accessTokenDTO.getCode() + "&redirect_uri=" + accessTokenDTO.getRedirectUri() + "&state=" + accessTokenDTO.getState())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //assert response.body() != null;
            String string = response.body().string();
            return string.split("&")[0].split("=")[1];
        } catch (Exception ignored) {
            return null;
        }
    }

    public GithubUser getUser(String accessToken){
        System.out.println("getUser使用的"+accessToken);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://api.github.com/user?access_token=" + accessToken)
            .build();
        try {
            Response response = client.newCall(request).execute();
            //assert response.body() != null;
            String string = response.body().string();
            return JSON.parseObject(string, GithubUser.class);
        } catch(IOException ignored){
            return null;
        }
    }
}
