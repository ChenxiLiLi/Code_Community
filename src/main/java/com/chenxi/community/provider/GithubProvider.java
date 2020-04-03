package com.chenxi.community.provider;

import com.alibaba.fastjson.JSON;
import com.chenxi.community.dto.AccessTokenDTO;
import com.chenxi.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Mr.Chen
 * @Description: Github登录，提供访问令牌对象和获取GithubUser对象
 * @Date:Created in 21:47 2020/3/2
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id=" + accessTokenDTO.getClientId() + "&client_secret=" + accessTokenDTO.getClientSecret() + "&code=" + accessTokenDTO.getCode() + "&redirect_uri=" + accessTokenDTO.getRedirectUri() + "&state=" + accessTokenDTO.getState())
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String string = response.body().string();
            return string.split("&")[0].split("=")[1];
        } catch (IOException ignored) {
            return null;
        } finally {
            response.close();
        }
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String string = response.body().string();
            return JSON.parseObject(string, GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            response.close();
        }
    }
}
