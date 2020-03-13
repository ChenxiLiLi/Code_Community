package com.chenxi.community.dto;

import lombok.Data;

/**
 * @Author: Mr.Chen
 * @Description: AccessToken Github登录的访问令牌对象，
 *               当第一次登录后，服务端生成一个Token便将此Token返回给客户端，
 *               以后客户端只需带上这个Token前来请求数据即可，无需再次带上用户名和密码，
 *         好处：减轻服务器的压力，减少频繁的查询数据库，使服务器更加健壮。
 * @Date:Created in 21:18 2020/3/2
 */
@Data
public class AccessTokenDTO {
    private String clientId;
    private String clientSecret;
    private String code;
    private String redirectUri;
    private String state;
}
