package com.atguigu.ggkt.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;
import springfox.documentation.spring.web.json.Json;

import java.io.StringReader;
import java.util.Date;

public class JWTHelper {

    //token 字符串有效时间
    private static long tokenExpiration = 60 * 1000;
    //加盟编码秘钥
    private static String tokenStringKey = "123456";

    //根据userid 和 username 生产token字符串
    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
                //设置token分类
                .setSubject("GGKT-USER")
                //token字符串有效时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                //私有部分（用户信息）
                .claim("userId", userId)
                .claim("userName", userName)
                //根据秘钥使用加密编码方式进行加密，对字符串压缩
                .signWith(SignatureAlgorithm.HS512, tokenStringKey)
                //数据压缩
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //从token字符串获取userid
    public static Long getUserId(String token) {
        if (StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenStringKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }

    //从token字符串获取getUserName
    public static String getUserName(String token) {
        if (StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenStringKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("userName");
    }

//    public static void main(String[] args) {
//        String token = JWTHelper.createToken(1L,"lucy");
//        System.out.println(token);
//        System.out.println(JWTHelper.getUserId(token));
//        System.out.println(JWTHelper.getUserName(token));
//    }
}
